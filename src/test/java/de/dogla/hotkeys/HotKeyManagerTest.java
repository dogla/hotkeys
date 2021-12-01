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

@SuppressWarnings({ "nls", "javadoc" })
public class HotKeyManagerTest {
	
	public static void main(String[] args) throws InterruptedException {
		HotKeyManager.getInstance().addHotKey("id1", HotKey.MOD_ALT, 'A', (event) -> {
			System.out.println("HotKey detected: " + event.getHotKey());
		});
		HotKeyManager.getInstance().addHotKey("id2", HotKey.MOD_CONTROL | HotKey.MOD_ALT, 'S', (event) -> {
			System.out.println("HotKey detected: " + event.getHotKey());
		});
		
		Thread.sleep(5_000);
		HotKeyManager.getInstance().removeHotKey("id2");		
		
		Thread.sleep(20_000);
	}

}
