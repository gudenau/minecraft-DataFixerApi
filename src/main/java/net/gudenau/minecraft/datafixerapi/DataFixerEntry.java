package net.gudenau.minecraft.datafixerapi;

import java.util.concurrent.atomic.AtomicInteger;
import net.fabricmc.api.*;
import net.fabricmc.loader.api.FabricLoader;
import net.gudenau.minecraft.datafixerapi.api.v0.ClientDataFixerAdder;
import net.gudenau.minecraft.datafixerapi.api.v0.DataFixerAdder;
import net.gudenau.minecraft.datafixerapi.api.v0.DataFixerApi;
import net.gudenau.minecraft.datafixerapi.impl.DataFixerApiImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Thanks to i509VCB for helping out, I referenced their pull request for a couple of the strange parts of this.
 * https://github.com/FabricMC/fabric/pull/522
 */
@EnvironmentInterface(value = EnvType.CLIENT, itf = ClientModInitializer.class)
public final class DataFixerEntry implements ModInitializer, ClientModInitializer{
    public static final String MOD_ID = "data-fixer-api";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    
    private final AtomicInteger initCount = new AtomicInteger(0);
    
    @Override
    public void onInitialize(){
        var loader = FabricLoader.getInstance();
        var api = DataFixerApi.getInstance();
        
        for(var entrypoint : loader.getEntrypointContainers(MOD_ID, DataFixerAdder.class)){
            try{
                entrypoint.getEntrypoint().addDataFixers(api);
            }catch(Throwable t){
                LOGGER.error(
                    "DataFixerAdder for %s failed with an unexpected exception".formatted(entrypoint.getProvider().getMetadata().getId()),
                    t
                );
                System.exit(1);
            }
        }
        
        if(loader.getEnvironmentType() == EnvType.CLIENT){
            if(initCount.incrementAndGet() == 2){
                DataFixerApiImpl.INSTANCE.lock();
            }
        }else{
            DataFixerApiImpl.INSTANCE.lock();
        }
    }
    
    @Environment(EnvType.CLIENT)
    @Override
    public void onInitializeClient(){var loader = FabricLoader.getInstance();
        var api = DataFixerApi.getInstance();
    
        for(var entrypoint : loader.getEntrypointContainers(MOD_ID, ClientDataFixerAdder.class)){
            try{
                entrypoint.getEntrypoint().addClientDataFixers(api);
            }catch(Throwable t){
                LOGGER.error(
                    "ClientDataFixerAdder for %s failed with an unexpected exception".formatted(entrypoint.getProvider().getMetadata().getId()),
                    t
                );
                System.exit(1);
            }
        }
        
        if(initCount.incrementAndGet() == 2){
            DataFixerApiImpl.INSTANCE.lock();
        }
    }
}
