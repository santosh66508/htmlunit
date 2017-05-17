/*
 * Copyright (c) 2002-2017 Gargoyle Software Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gargoylesoftware.htmlunit.javascript.host;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gargoylesoftware.htmlunit.BrowserRunner;
import com.gargoylesoftware.htmlunit.History;
import com.gargoylesoftware.htmlunit.TopLevelWindow;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebServerTestCase;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Tests for {@link History}.
 *
 * @author Daniel Gredler
 */
@RunWith(BrowserRunner.class)
public class HistoryTest extends WebServerTestCase {

    /**
     * Starts the web server prior to test execution.
     * @throws Exception if an error occurs
     */
    @Before
    public void setUp() throws Exception {
        startWebServer("src/test/resources/com/gargoylesoftware/htmlunit/javascript/host");
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    public void backAndForward() throws Exception {
        final WebClient client = getWebClient();
        final TopLevelWindow window = (TopLevelWindow) client.getCurrentWindow();
        final History history = window.getHistory();

        final String urlA = "http://localhost:" + PORT + "/HistoryTest_a.html";
        final String urlB = "http://localhost:" + PORT + "/HistoryTest_b.html";
        final String urlBX = "http://localhost:" + PORT + "/HistoryTest_b.html#x";
        final String urlC = "http://localhost:" + PORT + "/HistoryTest_c.html";

        HtmlPage page = client.getPage(urlA);
        assertEquals(1, history.getLength());
        assertEquals(0, history.getIndex());
        assertEquals(urlA, page.getUrl());

        page = page.getAnchorByName("b").click();
        assertEquals(2, history.getLength());
        assertEquals(1, history.getIndex());
        assertEquals(urlB, page.getUrl());

        page = page.getAnchorByName("x").click();
        assertEquals(3, history.getLength());
        assertEquals(2, history.getIndex());
        assertEquals(urlBX, page.getUrl());

        page = page.getAnchorByName("back").click();
        assertEquals(3, history.getLength());
        assertEquals(1, history.getIndex());
        assertEquals(urlB, page.getUrl());

        page = page.getAnchorByName("back").click();
        assertEquals(3, history.getLength());
        assertEquals(0, history.getIndex());
        assertEquals(urlA, page.getUrl());

        page = page.getAnchorByName("forward").click();
        assertEquals(3, history.getLength());
        assertEquals(1, history.getIndex());
        assertEquals(urlB, page.getUrl());

        page = page.getAnchorByName("c").click();
        assertEquals(3, history.getLength());
        assertEquals(2, history.getIndex());
        assertEquals(urlC, page.getUrl());

        page = page.getAnchorByName("back").click();
        assertEquals(3, history.getLength());
        assertEquals(1, history.getIndex());
        assertEquals(urlB, page.getUrl());

        page = page.getAnchorByName("forward").click();
        assertEquals(3, history.getLength());
        assertEquals(2, history.getIndex());
        assertEquals(urlC, page.getUrl());
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    public void go() throws Exception {
        final WebClient client = getWebClient();
        final TopLevelWindow window = (TopLevelWindow) client.getCurrentWindow();
        final History history = window.getHistory();

        final String urlA = "http://localhost:" + PORT + "/HistoryTest_a.html";
        final String urlB = "http://localhost:" + PORT + "/HistoryTest_b.html";
        final String urlBX = "http://localhost:" + PORT + "/HistoryTest_b.html#x";
        final String urlC = "http://localhost:" + PORT + "/HistoryTest_c.html";

        HtmlPage page = client.getPage(urlA);
        assertEquals(1, history.getLength());
        assertEquals(0, history.getIndex());
        assertEquals(urlA, page.getUrl());

        page = page.getAnchorByName("b").click();
        assertEquals(2, history.getLength());
        assertEquals(1, history.getIndex());
        assertEquals(urlB, page.getUrl());

        page = page.getAnchorByName("x").click();
        assertEquals(3, history.getLength());
        assertEquals(2, history.getIndex());
        assertEquals(urlBX, page.getUrl());

        page = page.getAnchorByName("minusTwo").click();
        assertEquals(3, history.getLength());
        assertEquals(0, history.getIndex());
        assertEquals(urlA, page.getUrl());

        page = page.getAnchorByName("plusOne").click();
        assertEquals(3, history.getLength());
        assertEquals(1, history.getIndex());
        assertEquals(urlB, page.getUrl());

        page = page.getAnchorByName("c").click();
        assertEquals(3, history.getLength());
        assertEquals(2, history.getIndex());
        assertEquals(urlC, page.getUrl());

        page = page.getAnchorByName("minusOne").click();
        assertEquals(3, history.getLength());
        assertEquals(1, history.getIndex());
        assertEquals(urlB, page.getUrl());

        page = page.getAnchorByName("plusTwo").click();
        assertEquals(3, history.getLength());
        assertEquals(1, history.getIndex());
        assertEquals(urlB, page.getUrl());
    }
}
