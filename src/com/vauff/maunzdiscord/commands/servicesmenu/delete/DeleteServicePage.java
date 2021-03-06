package com.vauff.maunzdiscord.commands.servicesmenu.delete;

import com.vauff.maunzdiscord.commands.servicesmenu.AbstractServiceActionPage;
import com.vauff.maunzdiscord.core.AbstractCommand;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class DeleteServicePage extends AbstractServiceActionPage
{
	public DeleteServicePage(MessageReceivedEvent trigger, AbstractCommand<MessageReceivedEvent> cmd) throws Exception
	{
		super(trigger, cmd);

		if (guildHasService)
		{
			int i = 0;

			if (services.contains("server-tracking"))
			{
				addChild(i++, new ServerTrackingDeletePage(trigger, cmd, "Server Tracking"));
			}
		}
	}

	@Override
	public String getTitle()
	{
		return ":x:  |  **Delete Existing Service:**";
	}
}
