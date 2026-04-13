package com.oreofactions.models;

public enum FactionRole {

    RECRUIT("Recruit", 1),
    MEMBER("Member", 2),
    MODERATOR("Moderator", 3),
    COLEADER("Co-Leader", 4),
    LEADER("Leader", 5);

    private final String name;
    private final int level;

    FactionRole(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public String getName() { return name; }
    public int getLevel() { return level; }

    public static FactionRole fromString(String str) {
        if (str == null) return null;
        try {
            return FactionRole.valueOf(str.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public boolean canAffect(FactionRole other) {
        if (other == null) return false;
        return this.level > other.level;
    }

    public boolean isAtLeast(FactionRole minimumRole) {
        if (minimumRole == null) return true;
        return this.level >= minimumRole.level;
    }

    public String getColor() {
        return switch (this) {
            case LEADER    -> "§c";
            case COLEADER  -> "§6";
            case MODERATOR -> "§e";
            case MEMBER    -> "§a";
            case RECRUIT   -> "§7";
        };
    }

    public String getColoredName() { return getColor() + name; }

    public static FactionRole[] getDescending() {
        return new FactionRole[]{ LEADER, COLEADER, MODERATOR, MEMBER, RECRUIT };
    }

    public static FactionRole[] getAscending() {
        return new FactionRole[]{ RECRUIT, MEMBER, MODERATOR, COLEADER, LEADER };
    }

    public boolean isLeadership() { return this.level >= MODERATOR.level; }
    public boolean isAdmin()      { return this.level >= COLEADER.level; }

    public FactionRole getNextRank() {
        return switch (this) {
            case RECRUIT   -> MEMBER;
            case MEMBER    -> MODERATOR;
            case MODERATOR -> COLEADER;
            case COLEADER  -> LEADER;
            case LEADER    -> null;
        };
    }

    public FactionRole getPreviousRank() {
        return switch (this) {
            case LEADER    -> COLEADER;
            case COLEADER  -> MODERATOR;
            case MODERATOR -> MEMBER;
            case MEMBER    -> RECRUIT;
            case RECRUIT   -> null;
        };
    }
}
