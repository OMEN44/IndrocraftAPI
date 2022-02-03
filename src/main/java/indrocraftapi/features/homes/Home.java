package indrocraftapi.features.homes;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Home {

    public Home(Player owner, String name, Location location) {
        setWorld(location.getWorld());
        setId(owner.getUniqueId() + name);
        setLocation(location);
        setHomeName(name);
        setOwner(owner);
        setPitch(location.getPitch());
        setYaw(location.getYaw());
        setX(location.getX());
        setY(location.getY());
        setZ(location.getZ());
        setOwnerUUID(owner.getUniqueId());
    }

    public Home(Player owner, String name, World world, Double x, Double y, Double z, Float pitch, Float yaw) {
        setWorld(world);
        setId(owner.getUniqueId() + name);
        setLocation(new Location(world, x, y, z, yaw, pitch));
        setHomeName(name);
        setOwner(owner);
        setPitch(pitch);
        setYaw(yaw);
        setX(x);
        setY(y);
        setZ(z);
        setOwnerUUID(owner.getUniqueId());
    }

    //home data
    private String homeName;
    private String id;
    private Player owner;
    private UUID ownerUUID;

    //location data
    private World world;
    private Double x;
    private Double y;
    private Double z;
    private Float pitch;
    private Float yaw;
    private Location location;
    private Location position;

    //getters
    public World getWorld() {
        return world;
    }
    public String getName() {
        return homeName;
    }
    public Player getOwner() {
        return owner;
    }
    public UUID getOwnerUUID() {
        return ownerUUID;
    }
    public String getId() {
        return id;
    }
    public Double getX() {
        return x;
    }
    public Double getY() {
        return y;
    }
    public Double getZ() {
        return z;
    }
    public Float getPitch() {
        return pitch;
    }
    public Float getYaw() {
        return yaw;
    }
    public Location getLocation() {
        return location;
    }
    public Location getPosition() {
        return position;
    }

    //setters
    public void setWorld(World world) {
        this.world = world;
    }
    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }
    public void setOwner(Player owner) {
        this.owner = owner;
    }
    public void setOwnerUUID(UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setX(Double x) {
        this.x = x;
    }
    public void setY(Double y) {
        this.y = y;
    }
    public void setZ(Double z) {
        this.z = z;
    }
    public void setPitch(Float pitch) {
        this.pitch = pitch;
    }
    public void setYaw(Float yaw) {
        this.yaw = yaw;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public void setPosition(Location position) {
        this.position = position;
    }
}
