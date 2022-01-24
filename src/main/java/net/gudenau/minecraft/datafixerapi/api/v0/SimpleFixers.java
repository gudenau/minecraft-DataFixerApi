package net.gudenau.minecraft.datafixerapi.api.v0;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.schemas.Schema;
import java.util.Map;
import java.util.Objects;
import net.gudenau.minecraft.datafixerapi.impl.Util;
import net.minecraft.datafixer.fix.*;

/**
 * Some basic data fixers that are likely to be useful as mods update.
 */
public interface SimpleFixers{
    /**
     * Creates a biome renaming data fixer. The map should be formatted so the old names are keys pointing to new names
     * as values.
     *
     * @param schema The schema to use
     * @param name The name of the fixer
     * @param names The names to change
     * @return The created fixer
     */
    static DataFix createBiomeRenameFix(Schema schema, String name, Map<String, String> names){
        Objects.requireNonNull(schema, "schema can not be null");
        Objects.requireNonNull(name, "name can not be null");
        Objects.requireNonNull(names, "names can not be null");
        return new BiomeRenameFix(schema, false, name, names);
    }
    
    /**
     * Creates a biome renaming data fixer. The map should be formatted so the old names are keys pointing to new names
     * as values.
     *
     * @param schema The schema to use
     * @param name The name of the fixer
     * @param names The names to change
     * @return The created fixer
     */
    static DataFix createBiomeRenameFix(Schema schema, String name, String... names){
        Objects.requireNonNull(schema, "schema can not be null");
        Objects.requireNonNull(name, "name can not be null");
        Objects.requireNonNull(names, "names can not be null");
        return createBiomeRenameFix(schema, name, Util.createMap(names));
    }
    
    /*
    static DataFix createBlockEntityRenameFix(Schema schema, String name, Map<String, String> names){
        Objects.requireNonNull(schema, "schema can not be null");
        Objects.requireNonNull(name, "name can not be null");
        Objects.requireNonNull(names, "names can not be null");
        return new BlockEntityRenameFix(schema, name, names);
    }
    
    static DataFix createBlockEntityRenameFix(Schema schema, String name, String... names){
        Objects.requireNonNull(schema, "schema can not be null");
        Objects.requireNonNull(name, "name can not be null");
        Objects.requireNonNull(names, "names can not be null");
        return createBlockEntityRenameFix(schema, name, Util.createMap(names));
    }
     */
    
    /**
     * Creates a block renaming data fixer. The map should be formatted so the old names are keys pointing to new names
     * as values.
     *
     * @param schema The schema to use
     * @param name The name of the fixer
     * @param names The names to change
     * @return The created fixer
     */
    static DataFix createBlockRenameFix(Schema schema, String name, Map<String, String> names){
        Objects.requireNonNull(schema, "schema can not be null");
        Objects.requireNonNull(name, "name can not be null");
        Objects.requireNonNull(names, "names can not be null");
        return BlockNameFix.create(schema, name, (oldName)->names.getOrDefault(oldName, oldName));
    }
    
    /**
     * Creates a block renaming data fixer. The map should be formatted so the old names are keys pointing to new names
     * as values.
     *
     * @param schema The schema to use
     * @param name The name of the fixer
     * @param names The names to change
     * @return The created fixer
     */
    static DataFix createBlockRenameFix(Schema schema, String name, String... names){
        Objects.requireNonNull(schema, "schema can not be null");
        Objects.requireNonNull(name, "name can not be null");
        Objects.requireNonNull(names, "names can not be null");
        return createBlockRenameFix(schema, name, Util.createMap(names));
    }
    
    /**
     * Creates an entity renaming data fixer. The map should be formatted so the old names are keys pointing to new
     * names as values.
     *
     * @param schema The schema to use
     * @param name The name of the fixer
     * @param names The names to change
     * @return The created fixer
     */
    static DataFix createEntityRenameFix(Schema schema, String name, Map<String, String> names){
        Objects.requireNonNull(schema, "schema can not be null");
        Objects.requireNonNull(name, "name can not be null");
        Objects.requireNonNull(names, "names can not be null");
        return new EntityRenameFix(name, schema, true){
            @Override
            protected String rename(String oldName){
                return names.getOrDefault(oldName, oldName);
            }
        };
    }
    
    /**
     * Creates an entity renaming data fixer. The map should be formatted so the old names are keys pointing to new
     * names as values.
     *
     * @param schema The schema to use
     * @param name The name of the fixer
     * @param names The names to change
     * @return The created fixer
     */
    static DataFix createEntityRenameFix(Schema schema, String name, String... names){
        Objects.requireNonNull(schema, "schema can not be null");
        Objects.requireNonNull(name, "name can not be null");
        Objects.requireNonNull(names, "names can not be null");
        return createEntityRenameFix(schema, name, Util.createMap(names));
    }
    
    /**
     * Creates an item renaming data fixer. The map should be formatted so the old names are keys pointing to new names
     * as values.
     *
     * @param schema The schema to use
     * @param name The name of the fixer
     * @param names The names to change
     * @return The created fixer
     */
    static DataFix createItemRenameFix(Schema schema, String name, Map<String, String> names){
        Objects.requireNonNull(schema, "schema can not be null");
        Objects.requireNonNull(name, "name can not be null");
        Objects.requireNonNull(names, "names can not be null");
        return ItemNameFix.create(schema, name, (oldName)->names.getOrDefault(oldName, oldName));
    }
    
    /**
     * Creates an item renaming data fixer. The map should be formatted so the old names are keys pointing to new names
     * as values.
     *
     * @param schema The schema to use
     * @param name The name of the fixer
     * @param names The names to change
     * @return The created fixer
     */
    static DataFix createItemRenameFix(Schema schema, String name, String... names){
        Objects.requireNonNull(schema, "schema can not be null");
        Objects.requireNonNull(name, "name can not be null");
        Objects.requireNonNull(names, "names can not be null");
        return createItemRenameFix(schema, name, Util.createMap(names));
    }
}
