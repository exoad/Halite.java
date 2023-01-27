package com.jackmeng.halite.def.builtin;

/**
 * A simple "match" the string guard. It provides
 * certain types of strictness seen in
 * {@link com.jackmeng.halite.def.builtin.use_StrGuard.strguard_Strict}.
 *
 * @author Jack Meng
 * @see com.jackmeng.halite.def.builtin.use_StrGuard.strguard_Strict
 */
public class use_StrGuard
        implements
        impl_DGuard
{

    public enum strguard_Strict {
        /**
         * To match all lower and uppercase of all possible letters
         */
        MATCH_CASE,

        /**
         * To only match with ignoring case
         */
        LENIENT,

        /**
         * Match using a regex, AKA String.matches()
         */
        MATCH_REGEX;
    }

    private strguard_Strict rule;
    private String target;

    /**
     * @param rule
     *            Rule to use
     * @param target
     *            The target string to try and match
     */
    public use_StrGuard(strguard_Strict rule, String target)
    {
        this.target = target;
        this.rule = rule;
    }

    public void rule(strguard_Strict e)
    {
        this.rule = e;
    }

    public strguard_Strict rule()
    {
        return rule;
    }

    public String target()
    {
        return target;
    }

    public void target(String e)
    {
        this.target = e;
    }

    @Override public boolean check(String supplement)
    {
        return rule == strguard_Strict.LENIENT ? supplement.equalsIgnoreCase(target)
                : rule == strguard_Strict.MATCH_CASE ? supplement.equals(target)
                        : rule == strguard_Strict.MATCH_REGEX ? supplement.matches(target) : false;
    }
}
