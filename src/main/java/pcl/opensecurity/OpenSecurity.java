package pcl.opensecurity;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pcl.opensecurity.common.CommonProxy;
import pcl.opensecurity.common.ContentRegistry;
import pcl.opensecurity.common.SoundHandler;
import pcl.opensecurity.networking.*;
import pcl.opensecurity.util.UsernameCache;

@Mod.EventBusSubscriber
@Mod(modid = OpenSecurity.MODID, name = "OpenSecurity", version = BuildInfo.versionNumber + "-" + BuildInfo.buildNumber, dependencies = "required-after:opencomputers", updateJSON = "http://modupdates.pc-logix.com/opensecurity")
public class OpenSecurity {
    public static final String MODID = "opensecurity";

    @Instance(value = MODID)
    public static OpenSecurity instance;

    @SidedProxy(clientSide = "pcl.opensecurity.client.ClientProxy", serverSide = "pcl.opensecurity.common.CommonProxy")
    public static CommonProxy proxy;
    public static Config cfg = null;

    public static boolean debug = false;
    public static int rfidRange;
    public static boolean enableplaySoundAt = false;
    public static boolean ignoreUUIDs = false;
    public static boolean registerBlockBreakEvent = true;

    public static final Logger logger = LogManager.getFormatterLogger(MODID);

    public static SimpleNetworkWrapper network;

    private static ContentRegistry contentRegistry = new ContentRegistry();
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        long time = System.nanoTime();
        cfg = new Config(new Configuration(event.getSuggestedConfigurationFile()));
        ContentRegistry.preInit();
    	MinecraftForge.EVENT_BUS.register(contentRegistry);
        proxy.registerSounds();
        SoundHandler.registerSounds();
        network = NetworkRegistry.INSTANCE.newSimpleChannel("OpenSecurity");
        registerBlockBreakEvent = cfg.registerBlockBreak;
        rfidRange = cfg.rfidMaxRange;
        proxy.preinit();
        int packetID = 0;
        network.registerMessage(OSPacketHandler.PacketHandler.class, OSPacketHandler.class, packetID++, Side.SERVER);
        network.registerMessage(HandlerKeypadButton.class, PacketKeypadButton.class, packetID++, Side.CLIENT);
        network.registerMessage(PacketBoltFire.class, PacketBoltFire.class, packetID++, Side.CLIENT);
        network.registerMessage(PacketProtectionAdd.Handler.class, PacketProtectionAdd.class, packetID++, Side.CLIENT);
        network.registerMessage(PacketProtectionRemove.Handler.class, PacketProtectionRemove.class, packetID++, Side.CLIENT);
        network.registerMessage(PacketProtectionSync.Handler.class, PacketProtectionSync.class, packetID++, Side.CLIENT);

        logger.info("Registered " + packetID + " packets");
        logger.info("Finished pre-init in %d ms", (System.nanoTime() - time) / 1000000);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        long time = System.nanoTime();
        proxy.init();
        ContentRegistry.init();
        UsernameCache.initCache(256);
        logger.info("Finished init in %d ms", (System.nanoTime() - time) / 1000000);
    }

    @SubscribeEvent
    public static void onRegisterModels(ModelRegistryEvent event) {
        proxy.registerModels();
    }
}
