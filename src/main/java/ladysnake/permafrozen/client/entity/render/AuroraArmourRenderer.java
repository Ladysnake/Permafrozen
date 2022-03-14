package ladysnake.permafrozen.client.entity.render;

import ladysnake.permafrozen.client.entity.model.AuroraArmourModel;
import ladysnake.permafrozen.item.AuroraArmourItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class AuroraArmourRenderer extends GeoArmorRenderer<AuroraArmourItem> {
    public AuroraArmourRenderer() {
        super(new AuroraArmourModel());
        this.headBone = "armorHead";
        this.bodyBone = "armorBody";
        this.rightArmBone = "armorRightArm";
        this.leftArmBone = "armorLeftArm";
        this.rightLegBone = "armorRightLeg";
        this.leftLegBone = "armorLeftLeg";
        this.rightBootBone = "armorRightBoot";
        this.leftBootBone = "armorLeftBoot";
    }
}
