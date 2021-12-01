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
 * The callback interface for handling the triggered hot key.
 *
 * @author Dominik Glaser
 * @since 1.0
 */
public interface HotKeyHandler {
	
	/**
	 * This method will be called if the hot key was triggered.
	 * 
	 * @param event the corresponding event
	 */
	public void run(HotKeyEvent event);

}
