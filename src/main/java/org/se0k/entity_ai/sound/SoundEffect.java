package org.se0k.entity_ai.sound;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundEffect {

    private final ProtocolManager protocolManager;

    public SoundEffect() {
        this.protocolManager = ProtocolLibrary.getProtocolManager();
    }

    public void soundEffect(Player player, Location location) {

        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.NAMED_SOUND_EFFECT);

        packet.getIntegers().write(0, (int) (location.getX() * 8))
                            .write(1, (int) (location.getY() * 8))
                            .write(2, (int) (location.getZ() * 8));

        packet.getFloat().write(0, 100.0f)
                         .write(1, 1.0f);

        packet.getLongs().write(0, 0L);

        packet.getSoundEffects().write(0, Sound.BLOCK_AMETHYST_BLOCK_CHIME);

        packet.getSoundCategories().write(0, EnumWrappers.SoundCategory.PLAYERS);


        protocolManager.sendServerPacket(player, packet);

    }


}
