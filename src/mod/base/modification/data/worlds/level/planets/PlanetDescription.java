package mod.base.modification.data.worlds.level.planets;

import game_logic.repositories.HaveIdentifier;
import game_logic.repositories.Identifier;
import javafx.scene.image.Image;
import mod.base.components.worlds.level.planets.SimplePlanet;
import mod.base.modification.data.textures.Texture;
import mod.base.modification.data.worlds.level.AbstractLevel;

import java.util.function.Function;

public final class PlanetDescription implements HaveIdentifier {
    private final String name;
    private final Function <AbstractPlanet, AbstractPlanetNoiseParameter> noiseParameter;
    private final Image icon;
    private final Function <AbstractLevel, AbstractPlanet> planetFunction;
    private final Identifier id;

    public PlanetDescription (Identifier id, Image icon, String name, Function <AbstractPlanet, AbstractPlanetNoiseParameter> noiseParameterFunction, Function <AbstractLevel, AbstractPlanet> planetFunction) {
        if (id == null)
            throw new NullPointerException ("invalid id");
        if (icon != null)
            this.icon = icon;
        else
            this.icon = Texture.UNKNOWN.getTexture(0);
        if (name != null && !name.isEmpty())
            this.name = name;
        else
            this.name = "unknown_name/" + id;
        if (noiseParameterFunction != null)
            this.noiseParameter = noiseParameterFunction;
        else
            this.noiseParameter = planet -> AbstractPlanetNoiseParameter.getInstance(planet.getLevel().getParameter().getSeed(), planet);
        if (planetFunction != null)
            this.planetFunction = planetFunction;
        else {
            PlanetDescription desc = this;
            this.planetFunction = level -> new SimplePlanet(id) {
                @Override
                public PlanetDescription description() {
                    return desc;
                }

                @Override
                public AbstractLevel getLevel() {
                    return level;
                }
            };
        }
        this.id = id;
    }

    public Image getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public Function <AbstractPlanet, AbstractPlanetNoiseParameter> getNoiseParameter() {
        return noiseParameter;
    }

    public Function <AbstractLevel, AbstractPlanet> getPlanetFunction() {
        return planetFunction;
    }

    @Override
    public Identifier getID() {
        return id;
    }
}
