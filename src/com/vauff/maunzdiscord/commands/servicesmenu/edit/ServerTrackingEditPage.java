package com.vauff.maunzdiscord.commands.servicesmenu.edit;

import com.vauff.maunzdiscord.core.AbstractCommand;
import com.vauff.maunzdiscord.core.AbstractMenuPage;
import com.vauff.maunzdiscord.core.Main;
import com.vauff.maunzdiscord.core.Util;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.io.File;

public class ServerTrackingEditPage extends AbstractMenuPage
{
	protected File file;
	protected JSONObject json;

	public ServerTrackingEditPage(MessageReceivedEvent trigger, AbstractCommand<MessageReceivedEvent> cmd)
	{
		super(trigger, cmd);

		addChild(0, (event) ->
		{
			json.put("enabled", !json.getBoolean("enabled"));

			try
			{
				FileUtils.writeStringToFile(file, json.toString(2), "UTF-8");
				show(this);
			}
			catch (Exception e)
			{
				Main.log.error("", e);
			}
		});
		addChild(1, (event) ->
		{
			json.put("mapCharacterLimit", !json.getBoolean("mapCharacterLimit"));

			try
			{
				FileUtils.writeStringToFile(file, json.toString(2), "UTF-8");
				show(this);
			}
			catch (Exception e)
			{
				Main.log.error("", e);
			}
		});
		addChild(2, (event) ->
		{
			AbstractMenuPage page = new ServerTrackingEditIP(trigger, cmd);

			try
			{
				show(page);
			}
			catch (Exception e)
			{
				Main.log.error("", e);
			}

			waitForReply(page.menu.getStringID(), trigger.getAuthor().getStringID());
		});
		addChild(3, (event) ->
		{
			AbstractMenuPage page = new ServerTrackingEditChannel(trigger, cmd);

			try
			{
				show(page);
			}
			catch (Exception e)
			{
				Main.log.error("", e);
			}

			waitForReply(page.menu.getStringID(), trigger.getAuthor().getStringID());
		});
	}

	@Override
	public void show() throws Exception
	{
		file = new File(Util.getJarLocation() + "data/services/server-tracking/" + trigger.getGuild().getStringID() + "/serverInfo.json");
		json = new JSONObject(Util.getFileContents(file));
		super.show();
	}

	@Override
	public String getTitle()
	{
		return ":pencil:  |  **Edit Existing Service: Server Tracking**";
	}

	@Override
	public String[] getItems()
	{
		return new String[] {
				"Enabled: " + "**" + StringUtils.capitalize(Boolean.toString(json.getBoolean("enabled"))) + "**",
				"Map Character Limit: " + "**" + StringUtils.capitalize(Boolean.toString(json.getBoolean("mapCharacterLimit"))) + "**",
				"Server IP: " + "**" + json.getString("serverIP") + ":" + json.getInt("serverPort") + "**",
				"Server Tracking Channel: " + "<#" + json.getLong("serverTrackingChannelID") + ">"
		};
	}
}
