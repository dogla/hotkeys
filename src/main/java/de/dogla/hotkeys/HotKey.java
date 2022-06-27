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

/**
 * A hot key.
 *
 * @author Dominik Glaser
 * @since 1.0
 */
public class HotKey {
	
	/** Either ALT key must be held down. */
	public static final int MOD_ALT = 0x0001;
	/** Either CTRL key must be held down. */
	public static final int MOD_CONTROL = 0x0002;
	/** Either SHIFT key must be held down. */
	public static final int MOD_SHIFT = 0x0004;
	/**
	 * Either WINDOWS key was held down. These keys are labeled with the
	 * Microsoft Windows logo.
	 */
	public static final int MOD_WIN = 0x0008;

	private String id;
	private int modifiers;
	private int keyCode;
	private Object data;

	/**
	 * Constructor.
	 * 
	 * @param id the id of this hit key
	 * @param modifiers the modifiers for this shortcut ({@link #MOD_ALT}, {@link #MOD_SHIFT}, {@link #MOD_CONTROL}, {@link #MOD_WIN})
	 * @param keyCode the key code for this shortcut
	 */
	public HotKey(String id, int modifiers, int keyCode) {
		this(id, modifiers, keyCode, null);
	}

	/**
	 * Constructor.
	 * 
	 * @param id the id of this hotkey
	 * @param modifiers the modifiers for this shortcut ({@link #MOD_ALT}, {@link #MOD_SHIFT}, {@link #MOD_CONTROL}, {@link #MOD_WIN})
	 * @param keyCode the key code for this shortcut
	 * @param data the data that can be associated with this hot key
	 */
	public HotKey(String id, int modifiers, int keyCode, Object data) {
		this.id = id;
		this.modifiers = modifiers;
		this.keyCode = keyCode;
		this.data = data;
	}

	/* (non-javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return HotKeyUtils.getShortCutText(this);
	}

	/**
	 * Returns the modifiers.
	 *
	 * @return the modifiers
	 */
	public int getModifiers() {
		return modifiers;
	}

	/**
	 * Returns the keyCode.
	 *
	 * @return the keyCode
	 */
	public int getKeyCode() {
		return keyCode;
	}
	
	/**
	 * @return the id of this hot key.
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @return the data associated with this hot key.
	 */
	public Object getData() {
		return data;
	}

}

