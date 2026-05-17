package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;
import io.jonasg.bob.Strategy;

@Buildable(strategy = { Strategy.PERMISSIVE, Strategy.ALLOW_NULLS })
public class PermissiveAndAllowNulls {
}
