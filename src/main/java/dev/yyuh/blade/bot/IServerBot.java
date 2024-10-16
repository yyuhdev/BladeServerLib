package dev.yyuh.blade.bot;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public interface IServerBot {
    String PROFILE_NAME = "$Bot";

    Property[] SKINS = new Property[] {
            new Property("textures", // https://mineskin.org/358793428
                    "ewogICJ0aW1lc3RhbXAiIDogMTcyMDE5OTk4MDU5OSwKICAicHJvZmlsZUlkIiA6ICIzOTg5OGFiODFmMjU0NmQxOGIyY2ExMTE1MDRkZGU1MCIsCiAgInByb2ZpbGVOYW1lIiA6ICI4YjJjYTExMTUwNGRkZTUwIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2Q0ZGE0OWM4ZmMyYjdkMzY5NjY5YzE5ZDY1OTQ4Mjc0NDI0N2RkZWY4NDEwNjQyODlkNDVhM2ZjMjY0MDI1ZiIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9",
                    "vwzsk0ntI+nd8fXRz+5Bd3ckO/TOGuebp5eMKE/6m4siRaQbtMUrwVlvMO3YzQvY20F8CvGd+b/zpfmxmxCVBYSb8Y89ILDBPr++h0bz25lRtOs67PRqRFWJ/bcvOWzxYygNhlhvNdv/kNcFxQAEr1qKLZM+PHqWXkubtvZ2UYU5HUBOKTO+AGFcimavd/0ir3IQecSLeBoCjuzNExD5JHtRTZuFExk68elbYsvgrc5RRuTmqaKIEn/HytaWPCDdi3V5Hi8COw8FrtmOgBZQfb61Le57K/UJQFogAFPo3JSBWz1vg2KB5kaAUrA6IwGgG4i9sk5BS0nQL/3CvNLuyIq7BgoyzuXrZJdJ2AYxplVLqGyUB8JsggpKFHv1In+0vhhTAyKf1sJu+n9GRPKq/8tgZNngvMW5nvaU1/r7XaZgi57Jf6dY2Ad/uBC6sPSo2Q5t4sdA58nVUQbRW2mE/5uvRGcnrZLHKuIv1OPJBa5tLy+aaT8aACMd0riUMCbWifemM5oaXto7LaF8Y+uvksYfJGvyJHKoVR16xpkUXJ3QipZD9go/B7olR9oPdOL+4MhU9TtwuR4Wv9WpJ2d7E+Nq5TFwV8GWuV90WnMCjI04yDmFGZ2YQdp5l7p5q72T0f0MemoAygjn3wosy8IUz9l4g/eKzwiT4d/HRLE7CFI="),
            new Property("textures", // https://mineskin.org/728604141
                    "ewogICJ0aW1lc3RhbXAiIDogMTcyMDIwMDAwMTc1OSwKICAicHJvZmlsZUlkIiA6ICJlODhjMjBiOTUyMTA0NTA0OThkMDU4OTA5ODVhOTQ2OSIsCiAgInByb2ZpbGVOYW1lIiA6ICJTY2huZWxsZXJUYWc0MjciLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDFkNDg4NDliOGViZTA1NTc1M2YzMjBiNGQwMTY0OTU0OWQxNTI0NmQwN2FmYWU0N2MxNjdlNjY5MjBjMWZiMiIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9",
                    "XfaM1GIlNAwBRAiwkfbdHzU6C5D1VLIhcNqyX/XZaeXA4tZhbDAku4ykc8k7QD5vM6VA9XNPUo81JMIalTbc/3lBsSbS40M3Q+0bbUHVJqfR1QN2pYwx+2DpBpQQqYZFpyPWG7oGUP6AfH83oXv2WloVXcYpQ5sfinu8ynGO3fHZN6kTQglzdT7RWY3EPL+2W3iZ7wV//qELHrSRr979iiHcXvBQKY+vdanXIvqQw8TWhJqba0CnMcPB6rcHtUIQOhPjXTqMZlSQ/kuI2ew6njfBC0Nmtf2/bSlAyDBUj8vqxrfXzWA1atsT53fw1do/kTbhpQ0/w7szYuj0CZjzIAx8kjLaGPyEz23eGwNDRYWm9usr7uTO1caRIQiQN0Ya9ebva6sNJ69RLoWfMxhtSNCCCRUpTWlYMg8vBNBMdeyRV4qn0S79UCpBuKXi+KsS605rdbgaPmYkrjdyf/Br2PD5QmfC2PCNe6LGGCQ9ygxK9b/AfoxWUKN04rrlA1Tsxc4bL+OkGbmyig4Un/6ykS9/3T4Fs+AMoav4yAjbRTZGOVOKMkZ3QrsX7s7vlptsym7EpzD9dakZXDRyLyy+q5+SId6khb5U4RlrKmni7NG3aijleN45MkQXCM+TPLj3O2ZaJDZn2FKo6v4SKlYBSoEJrEd43kjjonilfJZMBu8="),
            new Property("textures", // https://mineskin.org/1190145668
                    "ewogICJ0aW1lc3RhbXAiIDogMTcyMDIwMDAyNjk4NSwKICAicHJvZmlsZUlkIiA6ICI0M2NmNWJkNjUyMDM0YzU5ODVjMDIwYWI3NDE0OGQxYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJrYW1pbDQ0NSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9hYjBhNTAyYzEyNjkzMTBmZWVkN2U4Mjk1NzM2ZTkwYjBhYWRlNDJiMmJiMDc0ZjZmYTdlYmRhOGJiMzM1OTE1IiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=",
                    "TTZp0SLVxwAVSsxZm25iA4p+dWvAVKGktkt9qglAxNqb9FeJS66os1DcVXwSC4Iov83L7VeTpzlcwmFVgcF1pKZE/umwkXHq3BgzN0hCxZEd1k3xVHu69CGSqM5lxkuR7y/RELfvIDoBKn6ax8SlvOstdWpzvCQRGHLzMw7Mhi65Q9jGn5p4+j2cIuAwlHFyLuicii7mJhJGGUwGIslNmephsuiRpimLtLUaMzFSYgSI1NhL1D2u8px4PskROO7edB8TLH8Rnl/uoDTi8aXKDqq/vnuc2uVmp07Hx1f2MMZynJLkjGaoXn1rr+oLpd4npWl24gd2SFAgk9CY8oP3/tLUh4dBLuPTyy1XIJ4MuD0+c2EzcKAggAH7qYYwtGBMAq3voFZfBbISw4Sh18QtJGwzBxiJ2wKN9/XPQSqITFhpX5RaKWjl/HGG3UHiQ/AuPrqzJUJb4eEYn229j2aJqlvR4ONZMur5KmI7fjSHOWwzvMXbHb9icZAHshYpI3eR5BYuBt35zej3yTXnIfZby5tF7j+oDrxrQ6dHCSxqM3xsuLr4pqrwf0SLQiXtTbg7n4YNoMIcgBMbGFWvG+BzJUp5TQDYXx/9fS7J0yLywuJ3sV3MiulVqNZaGZiZ+JLQ2MoAvaTkgTDIpOxLFa2Jm541dziVknvNOEhR2ril014="),
            new Property("textures", // https://minesk.in/1814657815
                    "ewogICJ0aW1lc3RhbXAiIDogMTcyMDIwMDExNjQzMSwKICAicHJvZmlsZUlkIiA6ICJhMzdlOGYyM2MyMTk0NjJiYjY3ZWUxY2I5OTM2YWY2NiIsCiAgInByb2ZpbGVOYW1lIiA6ICJEb29yYWxleSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9lYTYyZDdiY2FlNjBlMGE3ZjZiOWZlZjE4OTIyYjIyOGI5YTQ2NGUxMTA2NGJmMmE1MGY3OTdhMjIyZTllMDZjIiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=",
                    "CLQWO1KnlUIdJK0Fmem5dcr6Kd2Q2Fi+a36ZVPFh9XBBxRn4E1w6Mafs3gVI3BKGf1Pj5Djv8w7fMShFTKPihrfzm4lD1YEH5SzX5z0zMGjiuG3QfRI+GodE1CkV/4F/llTuDliFlwNm4nnWtLOgURj9AGAV9IeN6MufRCQ5rZHHLwN20E2MwzDgkOpui+nuQghcjEDBBatXt2MhxpDW/vI1vrZJOUQL70u4C8oGEI1pE7yDH1trqW/GX9powrmhOQqePAoccD5nIvFhQ14sfwGDkcKPE6vQLSxgrAhtecQMgFZ/HjuIux8XpsrFfj2ITtoC+SLPocWTdHGdS2PHUEY6Y4HgVYQN9dt81xOzbdsYQ5gZXRbZpEItiRWPV4mR7OSerLFVofjYqfcTzkFi7PODlBedkgA8wWaibq8XdpXbHz7o95zDJjug/bOWwNOF/XOa0nv4qgh9/tQeAPBOi7+ehXjnJlkczCk806/7mecPkNM9EE2k7FUO9ej3MvmKEyoOM7ehW7d0wZ3Yv71jSDv+C/M7yaH2fsGjLT/mHmKwoMSo5cPGRKwjynAkiYzvdw4tZqeSYvUqNYVdvG0XLEw5+xO8bf9o+tN4z/IuByRux3EGQUBTzchK/407fxvXXheI3vZqO0OTXZIEX9IBXOauhrV+0VCEt7mA14PyjGA="),
    };

    ServerPlayer getSpawner();

    ServerBotSettings getSettings();

    ServerPlayer getVanillaPlayer();

    void destroy();

    static GameProfile getProfile() {
        GameProfile profile = new GameProfile(UUID.randomUUID(), PROFILE_NAME);
        PropertyMap properties = profile.getProperties();
        properties.put("textures", SKINS[ThreadLocalRandom.current().nextInt(SKINS.length)]);
        return profile;
    }
}
