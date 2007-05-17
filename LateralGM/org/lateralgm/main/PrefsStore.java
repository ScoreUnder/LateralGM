/*
 * Copyright (C) 2007 Quadduc <quadduc@gmail.com>
 * 
 * This file is part of Lateral GM.
 * Lateral GM is free software and comes with ABSOLUTELY NO WARRANTY.
 * See LICENSE for details.
 */

package org.lateralgm.main;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import org.lateralgm.jedit.SyntaxStyle;
import org.lateralgm.jedit.Token;

public class PrefsStore
	{
	private static final Preferences PREFS = Preferences.userRoot().node("/org/lateralgm");

	public static final ArrayList<String> getRecentFiles()
		{
		String value = PREFS.get("FILE_RECENT",null);
		if (value == null) return new ArrayList<String>(0);
		String[] array = value.split(" ");
		ArrayList<String> list = new ArrayList<String>(array.length);
		for (String name : array)
			list.add(Util.urlDecode(name));
		return list;
		}

	public static final void addRecentFile(String name)
		{
		int maxcount = PREFS.getInt("FILE_RECENT_COUNT",4);
		ArrayList<String> oldList = getRecentFiles();
		oldList.remove(name);
		String newList;
		newList = Util.urlEncode(name);
		for (int i = 0; i + 1 < maxcount && i < oldList.size(); i++)
			newList += " " + Util.urlEncode(oldList.get(i));
		PREFS.put("FILE_RECENT",newList);
		}

	public static final Rectangle getWindowBounds()
		{
		return Util.stringToRectangle(PREFS.get("WINDOW_BOUNDS",null),new Rectangle(800,600));
		}

	public static final void setWindowBounds(Rectangle r)
		{
		PREFS.put("WINDOW_BOUNDS",Util.rectangleToString(r));
		}

	public static final boolean getWindowMaximized()
		{
		return PREFS.getBoolean("WINDOW_MAXIMIZED",true);
		}

	public static final void setWindowMaximized(boolean b)
		{
		PREFS.putBoolean("WINDOW_MAXIMIZED",b);
		}

	public static final SyntaxStyle[] getSyntaxStyles()
		{
		SyntaxStyle[] styles = new SyntaxStyle[Token.ID_COUNT];
		styles[Token.COMMENT1] = new SyntaxStyle(new Color(0x338833),true,false);
		styles[Token.COMMENT2] = new SyntaxStyle(new Color(0x333388),true,false);
		styles[Token.KEYWORD1] = new SyntaxStyle(new Color(0x000000),false,true);
		styles[Token.KEYWORD2] = new SyntaxStyle(new Color(0x1111DD),true,false);
		styles[Token.KEYWORD3] = new SyntaxStyle(new Color(0x770077),false,false);
		styles[Token.LITERAL1] = new SyntaxStyle(new Color(0x660099),false,false);
		styles[Token.LITERAL2] = new SyntaxStyle(new Color(0x771111),false,false);
		styles[Token.LABEL] = new SyntaxStyle(new Color(0x111177),false,false);
		styles[Token.OPERATOR] = new SyntaxStyle(new Color(0x000000),false,true);
		styles[Token.INVALID] = new SyntaxStyle(new Color(0xEE0000),false,true);
		for (int i = 1; i < styles.length; i++)
			{
			String key = String.format("SYNTAX_STYLE_%02X",i);
			styles[i] = Util.stringToSyntaxStyle(PREFS.get(key,null),styles[i]);
			}
		return styles;
		}
	}