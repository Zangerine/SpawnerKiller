package tc.spawnerkiller;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class SpawnerKiller extends JavaPlugin implements Listener {

    List<String> whitelistedWorlds = new ArrayList<>();

    @Override
    public void onEnable() {
        whitelistedWorlds.addAll(getConfig().getStringList("world-whitelist"));
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this,this);
    }

    @EventHandler
    public void spawnEvent(SpawnerSpawnEvent e) {
        Entity en = e.getEntity();
        World w = e.getEntity().getWorld();
        String n = w.getName();
        if(whitelistedWorlds.contains(n)) {
            if (e.getEntityType() == EntityType.BLAZE) {
                if (Math.random() < 0.5) {
                    e.getEntity().getWorld().dropItemNaturally(e.getLocation(), new ItemStack(Material.BLAZE_ROD, 1));
                }
                ((ExperienceOrb)en.getWorld().spawn(en.getLocation(), (Class) ExperienceOrb.class)).setExperience(10);
                e.setCancelled(true);
                return;
            }
            ((Damageable)en).damage(1000.0);
            en.remove();
            EntityType entype = e.getEntityType();
            if (entype == EntityType.GUARDIAN || entype == EntityType.BLAZE) {
                ((ExperienceOrb)en.getWorld().spawn(en.getLocation(), (Class)ExperienceOrb.class)).setExperience(10);
            }
            else if (entype == EntityType.OCELOT || entype == EntityType.CHICKEN || entype == EntityType.COW || entype == EntityType.HORSE || entype == EntityType.MUSHROOM_COW || entype == EntityType.PIG || entype == EntityType.RABBIT || entype == EntityType.SHEEP || entype == EntityType.SQUID || entype == EntityType.WOLF) {
                int randomNum = 1 + (int)(Math.random() * 3.0);
                ((ExperienceOrb)en.getWorld().spawn(en.getLocation(), (Class)ExperienceOrb.class)).setExperience(randomNum);
            }
            else if (entype == EntityType.CAVE_SPIDER || entype == EntityType.CREEPER || entype == EntityType.ENDERMAN || entype == EntityType.GHAST || entype == EntityType.SILVERFISH || entype == EntityType.SKELETON || entype == EntityType.SPIDER || entype == EntityType.WITCH || entype == EntityType.ZOMBIE || entype == EntityType.PIGLIN) {
                if(entype == EntityType.SPIDER && Math.random() < 0.5){
                    e.getEntity().getWorld().dropItemNaturally(e.getLocation(), new ItemStack(Material.SPIDER_EYE, 1));
                }
                ((ExperienceOrb)en.getWorld().spawn(en.getLocation(), (Class)ExperienceOrb.class)).setExperience(5);
            }
            else if (entype == EntityType.SLIME || entype == EntityType.MAGMA_CUBE) {
                ((ExperienceOrb)en.getWorld().spawn(en.getLocation(), (Class)ExperienceOrb.class)).setExperience(3);
            }
        }
    }

    @EventHandler
    public void riderBlockEvent(CreatureSpawnEvent e) {
        World w = e.getEntity().getWorld();
        String n = w.getName();
        if(whitelistedWorlds.contains(n)) {
            if(e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER)) e.setCancelled(true);
        }
    }
}
