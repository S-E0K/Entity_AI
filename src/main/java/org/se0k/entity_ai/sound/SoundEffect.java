package org.se0k.entity_ai.sound;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SoundEffect {

    public void soundEffect(Player player, Location location, float volume, float pitch) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

        PacketContainer packetContainer = new PacketContainer(PacketType.Play.Server.NAMED_SOUND_EFFECT);





    }


}
