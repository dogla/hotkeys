/**
 * Copyright (C) 2009-2021 Dominik Glaser
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
package de.dogla.hotkeys;

import java.util.HashMap;
import java.util.Map;

/**
 * The hot key manager.
 *
 * @author Dominik Glaser
 * @since 1.0
 */
public class HotKeyManager {

    private static final HotKeyManager INSTANCE = new HotKeyManager();
    
    /**
     * @return the singleton instance
     */
    public static HotKeyManager getInstance() {
    	return INSTANCE;
    }

    private Map<String, HotKeyWindowProc> windowProcs = new HashMap<>();

	/**
	 * Removes the hot key with the given id.
	 * 
	 * @param id the id of the hot key
	 */
	public void removeHotKey(String id) {
		HotKeyWindowProc windowProc = windowProcs.remove(id);
		if (windowProc != null) {
			windowProc.unregister();
		}
	}

	/**
	 * Removes the given hot key.
	 * 
	 * @param hotKey the hot key to register
	 */
	public void removeHotKey(HotKey hotKey) {
		if (hotKey != null) {
			removeHotKey(hotKey.getId());
		}
	}

	/**
	 * Registers the given hot key.
	 * 
	 * @param id the id of the hot key
	 * @param modifiers the modifiers
	 * @param keyCode the key code
	 * @param hotKeyHandler the callback handler that will be executed if the corresponding hot key was triggered by the user.
	 * 
	 * @return <code>true</code> if the hot key could successfully be registered by the OS. 
	 */
	public boolean addHotKey(String id, int modifiers, int keyCode, HotKeyHandler hotKeyHandler) {
		return addHotKey(new HotKey(id, modifiers, keyCode), hotKeyHandler);
	}
	
	/**
	 * Registers the given hot key.
	 * 
	 * @param hotKey the hot key
	 * @param hotKeyHandler the callback handler that will be executed if the corresponding hot key was triggered by the user.
	 * 
	 * @return <code>true</code> if the hot key could successfully be registered by the OS. 
	 */
	public boolean addHotKey(HotKey hotKey, HotKeyHandler hotKeyHandler) {
		if (windowProcs.containsKey(hotKey.getId())) {
			throw new IllegalStateException("A hot key with the given ID was already registered: " + hotKey.getId()); //$NON-NLS-1$
		}
		HotKeyWindowProc entry = new HotKeyWindowProc(hotKey, hotKeyHandler);
		boolean register = entry.register();
		if (register) {
			windowProcs.put(hotKey.getId(), entry);
		}
		return register;
	}
	
}
