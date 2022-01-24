/*TODO
package net.gudenau.minecraft.datafixerapi.fixers;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice;
import java.util.Map;
import net.minecraft.datafixer.TypeReferences;

public class BlockEntityRenameFix extends DataFix{
    private final String name;
    private final Map<String, String> names;
    
    public BlockEntityRenameFix(Schema schema, String name, Map<String, String> names){
        super(schema, true);
        this.name = name;
        this.names = names;
    }
    
    @Override
    protected TypeRewriteRule makeRule(){
        var inputType = getInputSchema().getType(TypeReferences.ITEM_STACK);
        var outputType = getOutputSchema().getType(TypeReferences.ITEM_STACK);
        var inputChoice = getInputSchema().findChoiceType(TypeReferences.BLOCK_ENTITY);
        var outputChoice = getOutputSchema().findChoiceType(TypeReferences.BLOCK_ENTITY);
        return TypeRewriteRule.seq(
            convertUnchecked("ItemStack BlockEntity hook for " + name, inputType, outputType),
            fixTypeEverywhere(name, inputChoice, outputChoice, (dynamicOps)->(pair)->pair.mapFirst((rawName)->{
                if(rawName instanceof String name){
                    return names.getOrDefault(name, name);
                }else{
                    throw new IllegalStateException("How?");
                }
            }))
        );
        return null;
    }
}
*/
