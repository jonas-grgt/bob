package io.jonasg.bob.definitions;

import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.Set;

public class ConstructorDefinition {

    private List<ParameterDefinition> parameters;
    private Set<Modifier> modifiers;

    public ConstructorDefinition(List<ParameterDefinition> parameters, Set<Modifier> modifiers) {
        this.parameters = parameters;
        this.modifiers = modifiers;
    }

    public boolean isPrivate() {
        return modifiers.contains(Modifier.PRIVATE);
    }

    public List<ParameterDefinition> parameters() {
        return parameters;
    }
}
