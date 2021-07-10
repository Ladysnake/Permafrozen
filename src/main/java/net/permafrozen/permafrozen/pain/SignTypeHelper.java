package net.permafrozen.permafrozen.pain;

import net.minecraft.util.SignType;

import java.util.Set;

public interface SignTypeHelper {
	Set<SignType> pf_getTypes();
	
	static SignType register(SignType type) {
		((SignTypeHelper) type).pf_getTypes().add(type);
		return type;
	}
}
