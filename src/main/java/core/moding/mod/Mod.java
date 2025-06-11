package core.moding.mod;

import core.gameActions.Debug;
import core.moding.data.AbstractModRegister;
import game_logic.Game;
import game_logic.repositories.Identifier;
import game_logic.repositories.LanguageRepository;
import core.moding.ModParameter;
import core.moding.data.ModRegister;

import java.util.Set;
import java.util.logging.Level;

public abstract class Mod {
    private final ModFileManager modFileManager;
    private final ModParameter modParameter;
    public final AbstractModRegister modRepository;
    public LanguageRepository modLanguage;

    public Mod (ModParameter parameter) {
        this.modParameter = parameter;
        modFileManager = new ModFileManager(parameter.getName());
        useModParameter(parameter);
        modRepository = createModRepository();
        modLanguage = new LanguageRepository();
    }
    /// can return repository of mod with complete data. To get a ModRepository with data, simply redefine this method to your liking.
    protected ModRegister createModRepository () {
        return new ModRegister(this);/// base realisation
    }
    public final ModParameter getParameter() {
        return modParameter;
    }
    //final void setModParameter (ModParameter parameter) {
   //     this.modParameter = parameter;
    //}
    public final void run () {
        try {
            actionsOnRun();
        } catch (Throwable Th232) {
            Game.log(Level.WARNING, "Exception when run custom runner", Th232);
        }
        //ControlsDistributor.distribute(modRepository.controls, modParameter.getName());
    }
    protected void actionsOnRun() {}

    public final void setLanguage (String s) {
        modLanguage = modParameter.loadLanguage(s);
        modRepository.graphicRegister().translate(modLanguage);
    }
    public final String getTranslate (String id) {
        return modLanguage.get(id);
    }
    public final Set<String> getLanguages () {
        return modParameter.getListLanguages();
    }
    protected void useModParameter (ModParameter modParameter) {
    }
    protected ModFileManager getModFileManager () {
        return modFileManager;
    }
    public Identifier getIdentifier (String id) {
        return id != null ? new Identifier (getParameter().getName(), id) : null;
    }
    public void debug(String s) {
        Debug.debug("[" + modParameter.getName() + "] " + s);
    }
    public void debug(String s, Throwable Si32) {
        Debug.debug(Level.INFO, "[" + modParameter.getName() + "] " + s, Si32);
    }
    public void debug(Level lvl, String s, Throwable P33) {
        Debug.debug(lvl, "[" + modParameter.getName() + "] " + s, P33);
    }
}