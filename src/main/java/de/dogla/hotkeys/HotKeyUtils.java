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

import java.awt.event.KeyEvent;

/**
 * Utility class for hot key stuff.
 *
 * @author Dominik Glaser
 * @since 1.0
 */
@SuppressWarnings("nls")
public class HotKeyUtils {
	
	/**
	 * @param hotKey the hot key
	 * 
	 * @return the hot key as <code>String</code>
	 */
	public static String getShortCutText(HotKey hotKey) {
		StringBuilder sb = new StringBuilder();
		String separator = "";
		if (hasWINModifier(hotKey.getModifiers())) {
			sb.append(separator).append("WIN");
			separator = "+";
		}
		if (hasALTModifier(hotKey.getModifiers())) {
			sb.append(separator).append("ALT");
			separator = "+";
		}
		if (hasCTRLModifier(hotKey.getModifiers())) {
			sb.append(separator).append("CTRL");
			separator = "+";
		}
		if (hasSHIFTModifier(hotKey.getModifiers())) {
			sb.append(separator).append("SHIFT");
			separator = "+";
		}
		sb.append(separator).append(KeyEvent.getKeyText(hotKey.getKeyCode()));
		return sb.toString();
	}
	
	/**
	 * @param modifiers the modifiers
	 * 
	 * @return <code>true</code> if the given modifiers include the WIN modifier
	 */
	public static boolean hasWINModifier(int modifiers) {
		return (modifiers & HotKey.MOD_WIN) > 0;
	}
	
	/**
	 * @param modifiers the modifiers
	 * 
	 * @return <code>true</code> if the given modifiers include the ALT modifier
	 */
	public static boolean hasALTModifier(int modifiers) {
		return (modifiers & HotKey.MOD_ALT) > 0;
	}
	
	/**
	 * @param modifiers the modifiers
	 * 
	 * @return <code>true</code> if the given modifiers include the SHIFT modifier
	 */
	public static boolean hasSHIFTModifier(int modifiers) {
		return (modifiers & HotKey.MOD_SHIFT) > 0;
	}
	
	/**
	 * @param modifiers the modifiers
	 * 
	 * @return <code>true</code> if the given modifiers include the CTRL modifier
	 */
	public static boolean hasCTRLModifier(int modifiers) {
		return (modifiers & HotKey.MOD_CONTROL) > 0;
	}
	
}
