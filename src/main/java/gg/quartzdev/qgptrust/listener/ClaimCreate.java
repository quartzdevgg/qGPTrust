package gg.quartzdev.qgptrust.listener;

import me.ryanhamshire.GriefPrevention.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import me.ryanhamshire.GriefPrevention.events.ClaimCreatedEvent;

import java.util.ArrayList;

public class ClaimCreate implements Listener {

    @EventHandler
    public void onClaimCreate(ClaimCreatedEvent event){
        DataStore gpDataStore = GriefPrevention.instance.dataStore;
        Claim newClaim = event.getClaim();
        CommandSender owner = event.getCreator();

//        Makes sure creator is a player
        if(!(owner  instanceof Player)) return;

        Player player = (Player) owner;
        PlayerData playerData;

//        Makes sure player exists in GP Data
        try {
            playerData = gpDataStore.getPlayerData(player.getUniqueId());
        } catch(Exception e){
            return;
        }

//        Gets the last claim they made before the current one
        Claim lastClaim = playerData.lastClaim;
//        If last claim is null, then no trustees need to be added
        if(lastClaim == null) return;

//        initializes all trustee types lists
        ArrayList<String> builders = new ArrayList<>();
        ArrayList<String> containers = new ArrayList<>();
        ArrayList<String> accessors = new ArrayList<>();
        ArrayList<String> managers = new ArrayList<>();
//        gets all trustees of the last claim
        lastClaim.getPermissions(builders, containers, accessors, managers);

//        adds each type of trustee to the new claim respectively
        for(String builder : builders) {
            newClaim.setPermission(builder, ClaimPermission.Build);
        }
        for(String container : containers) {
            newClaim.setPermission(container, ClaimPermission.Inventory);
        }
        for(String accessor : accessors) {
            newClaim.setPermission(accessor, ClaimPermission.Access);
        }
        for(String manager : managers) {
            newClaim.setPermission(manager, ClaimPermission.Manage);
        }

        // Saves Claim to datastore making it effective
        gpDataStore.saveClaim(newClaim);

    }

}
