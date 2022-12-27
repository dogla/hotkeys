/**
 * Copyright (C) 2009-2022 Dominik Glaser
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.dogla.hotkeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.ATOM;
import com.sun.jna.platform.win32.WinDef.HMODULE;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.MSG;
import com.sun.jna.platform.win32.WinUser.WNDCLASSEX;
import com.sun.jna.platform.win32.WinUser.WindowProc;

/**
 * The WindowProc implementation for the hotkey handling.
 *
 * @author Dominik Glaser
 * @since 1.0
 */
@SuppressWarnings("nls")
class HotKeyWindowProc implements WindowProc {
	
	private static Logger logger = LoggerFactory.getLogger(HotKeyWindowProc.class);
	
	private static int lastId;
	private static final int MSG_UNREGISTER_HOTKEY = WinUser.WM_USER + 42;
	
	private HotKey hotKey;
	private HotKeyHandler handler;
	
	private String windowClass;
	private HMODULE hInst;
    private HWND hWnd;
	private int id;

	HotKeyWindowProc(HotKey hotKey, HotKeyHandler handler) {
		this.hotKey = hotKey;
		this.handler = handler;
	}

	@Override
	public LRESULT callback(HWND hwnd, int uMsg, WPARAM wParam, LPARAM lParam) {
		if (uMsg == WinUser.WM_HOTKEY) {
			logger.debug("HotKey '{}' deteted.", hotKey.getId());
			handler.run(new HotKeyEvent(hotKey));
		}
		return User32.INSTANCE.DefWindowProc(hwnd, uMsg, wParam, lParam);
	}
	
	/**
	 * @return registers the hot key
	 */
	boolean register() {
		boolean[] initialized = new boolean[1];
		boolean[] success = new boolean[] { true };
		Thread thread = new Thread(() -> {
			id = lastId++;
			windowClass = "HotKeyWindowClass"+id;
			hInst = Kernel32.INSTANCE.GetModuleHandle(null);
			if (hInst == null) {
				logger.error("Kernel32.GetModuleHandle not successful: {}", getLastError());
				initialized[0] = true;
				success[0] = false;
				return;
			}

			WNDCLASSEX wClass = new WNDCLASSEX();
			wClass.hInstance = hInst;
			wClass.lpfnWndProc = HotKeyWindowProc.this;
			wClass.lpszClassName = windowClass;
			
			// register window class
			ATOM result = User32.INSTANCE.RegisterClassEx(wClass);
			if (result.intValue() == 0) {
				logger.error("User32.RegisterClassEx not successful: {}", getLastError());
				initialized[0] = true;
				success[0] = false;
				return;
			}

			// create new window
			hWnd = User32.INSTANCE.CreateWindowEx(
							User32.WS_EX_TOPMOST,
							windowClass,
							"HotKey window: " + hotKey.getId() + " (" + hotKey + ")",
							WinUser.WS_OVERLAPPEDWINDOW, 0, 0, 100, 100,
							null, // WM_DEVICECHANGE contradicts parent=WinUser.HWND_MESSAGE
							null, hInst, null);

			if (hWnd == null) {
				logger.error("User32.CreateWindowEx not successful: {}", getLastError());
				initialized[0] = true;
				success[0] = false;
				return;
			}
			
			if (!User32.INSTANCE.RegisterHotKey(hWnd, id, hotKey.getModifiers(), hotKey.getKeyCode())) {
				logger.error("User32.UnregisterHotKey not successful: {}", getLastError());
				initialized[0] = true;
				success[0] = false;
				return;
			}
			
			initialized[0] = true;
			
			MSG msg = new MSG();
			while (User32.INSTANCE.GetMessage(msg, hWnd, 0, 0) != 0) {
				if (MSG_UNREGISTER_HOTKEY == msg.message) {
					break;
				}
				User32.INSTANCE.TranslateMessage(msg);
				User32.INSTANCE.DispatchMessage(msg);
			}

			if (!User32.INSTANCE.UnregisterHotKey(hWnd.getPointer(), id)) {
				logger.error("User32.UnregisterHotKey not successful: {}", getLastError());
				logger.error("Hot key '{}' {} not successfully unregistered.", hotKey.getId(), hotKey);
				return;
			}
			if (!User32.INSTANCE.DestroyWindow(hWnd)) {
				logger.error("User32.DestroyWindow not successful: {}", getLastError());
				logger.error("Hot key '{}' {} not successfully unregistered.", hotKey.getId(), hotKey);
				return;
			}
			if (!User32.INSTANCE.UnregisterClass(windowClass, hInst)) {
				logger.error("User32.UnregisterClass not successful: {}", getLastError());
				logger.error("Hot key '{}' {} not successfully unregistered.", hotKey.getId(), hotKey);
				return;
			}
			logger.debug("Hot key '{}' {} successfully unregistered.", hotKey.getId(), hotKey);
		});
		thread.setName("HotKey Listener " + hotKey.toString());
		thread.setDaemon(true);
		thread.start();
		
		while (!initialized[0]) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
				Thread.currentThread().interrupt();
			}
		}
		
		if (success[0]) {
			logger.debug("Registered hot key '{}': {}", hotKey.getId(), hotKey);
		} else {
			logger.error("HotKey could not be registered: {}", hotKey.getId());
		}
		return success[0];
	}
	
	/**
	 * Unregisters the hot key.
	 */
	void unregister() {
		User32.INSTANCE.PostMessage(hWnd, MSG_UNREGISTER_HOTKEY, new WPARAM(0), new LPARAM(0));
	}

	/**
	 * Gets the last error.
	 * 
	 * @return the last error
	 */
	int getLastError() {
		return Kernel32.INSTANCE.GetLastError();
	}
	
}