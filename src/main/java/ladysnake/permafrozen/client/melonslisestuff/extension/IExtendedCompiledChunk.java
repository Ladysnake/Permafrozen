package ladysnake.permafrozen.client.melonslisestuff.extension;

/**
 * @author Melonslise
 * https://github.com/Melonslise/NuclearWinter/blob/main/src/main/java/melonslise/nwinter/client/extension/IExtendedCompiledChunk.java
 * **/
public interface IExtendedCompiledChunk {
    long getSkylightBuffer();

    void freeSkylightBuffer();
}
