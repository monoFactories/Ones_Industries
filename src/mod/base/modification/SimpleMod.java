package mod.base.modification;

import core.moding.ModParameter;
import core.moding.data.AbstractModRegister;
import core.moding.mod.Mod;
import mod.base.modification.repositories.BlockRepository;
import mod.base.modification.repositories.SimpleModRepository;
import mod.base.modification.repositories.sounds.SoundRepository;

public class SimpleMod extends Mod {

    public SimpleMod(ModParameter parameter) {
        super(parameter);
    }

    @Override
    protected AbstractModRegister createModRepository() {
        SimpleModRepository simpleModRepository = new SimpleModRepository (this);
        String name = getParameter().getName();
        BlockRepository.BLOCK_REGISTER.addEntry(name, simpleModRepository.blocks);
        SoundRepository.sounds.addEntry (name, simpleModRepository.media);
        return simpleModRepository;
    }
}