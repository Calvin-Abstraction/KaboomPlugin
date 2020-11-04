package edu.calvin.abs.kaboomplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Random;

public class ExplosionListener implements Listener {
    private static final Random RANDOM = new Random();
    private static final double BLAST_RADIUS_BOUND = 6.0;
    private static final double RANDOM_OFFSET_SCALE = 0.08;

    /**
     * @return A random positive or negative number between -RANDOM_OFFSET_SCALE and +RANDOM_OFFSET_SCALE.
     */
    private double getRandomOffset() {
        return -RANDOM_OFFSET_SCALE + (RANDOM_OFFSET_SCALE - RANDOM_OFFSET_SCALE) * RANDOM.nextDouble();
    }

    @EventHandler
    public void onEntityExplosion(EntityExplodeEvent event) {
        Location sourceLocation = event.getEntity().getLocation(); // The source of the explosion.
        double sourceX = sourceLocation.getX();
        double sourceY = sourceLocation.getY();
        double sourceZ = sourceLocation.getZ();

        World world = sourceLocation.getWorld(); // The current world.
        List<Block> blocksInBlastRadius = event.blockList();

        // We need to loop through the blocks and give them
        // velocity away from the source of the explosion.
        for (int i = 0; i < blocksInBlastRadius.size(); i++) {
            Block block = blocksInBlastRadius.get(i);
            if (block.getType() == Material.TNT) // Let TNT blocks explode.
                continue;

            double diffX = block.getX() - sourceX;
            double diffY = block.getY() - sourceY;
            double diffZ = block.getZ() - sourceZ;
            double distance = block.getLocation().distance(sourceLocation);

            // If the block is closer, it should have a lower chance of flying away.
            int bound = (int) Math.round(BLAST_RADIUS_BOUND / distance);
            if (RANDOM.nextInt(bound) != 0)
                continue;

            // Add some randomness to the velocities.
            double velX = getRandomOffset() + (diffX * KaboomPlugin.X_MODIFIER);
            double velY = RANDOM.nextDouble() + (diffY * KaboomPlugin.Y_MODIFIER);
            double velZ = getRandomOffset() + (diffZ * KaboomPlugin.Z_MODIFIER);
            Vector velocity = new Vector(velX, velY, velZ);

            // Spawn the falling block entity and set the velocity.
            FallingBlock blockEntity = world.spawnFallingBlock(block.getLocation(), block.getBlockData());
            blockEntity.setVelocity(velocity);
        }
    }
}
