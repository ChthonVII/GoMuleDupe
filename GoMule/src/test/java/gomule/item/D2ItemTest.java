package gomule.item;

import com.google.common.io.BaseEncoding;
import gomule.util.D2BitReader;
import org.junit.jupiter.api.Test;
import randall.d2files.D2TxtFile;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class D2ItemTest {

    public static final byte[] HEALTH_POT = new byte[]{16, 4, -96, 8, 21, 0, 0, 79, -76, 0};
    public static final byte[] SMALL_CHARM = new byte[]{16, 0, -128, 0, 5, 36, 68, -40, 79, -40, -114, -124, 14, 11, 80, -80, 12, 0, -76, 120, -10, 31};

    @Test
    public void viridianSmallCharm() throws Exception {
        String expected = "Viridian Small Charm\n" +
                "Small Charm\n" +
                "Required Level: 10\n" +
                "Fingerprint: 0x61d091db\n" +
                "Item Level: 1\n" +
                "Version: Resurrected\n" +
                "Poison Resist +7%\n";
        runItemDumpComparison(expected, loadD2Item(SMALL_CHARM));
    }

    @Test
    public void maras() throws Exception {
        String expected = "Mara's Kaleidoscope\n" +
                "Amulet\n" +
                "Required Level: 67\n" +
                "Fingerprint: 0x68fe8447\n" +
                "Item Level: 87\n" +
                "Version: Resurrected\n" +
                "+2 to All Skills\n" +
                "All Stats +5\n" +
                "All Resistances +26\n";
        byte[] bytes = {16, 0, -128, 0, -115, 8, -32, 89, 24, -114, 8, -3, -47, -82, 55, 32, 2, -128, -110, 0, 37, 1, -91, 1, -91, 19, -30, 82, -120, 91, 33, -82, -123, -72, 63, -6, 15};
        runItemDumpComparison(expected, loadD2Item(bytes));
    }

    @Test
    public void aldursBoots() throws Exception {
        String expected = "Aldur's Advance\n" +
                "Battle Boots\n" +
                "Defense: 42\n" +
                "Durability: 11 of 18\n" +
                "Required Level: 45\n" +
                "Required Strength: 95\n" +
                "Fingerprint: 0x7bc09616\n" +
                "Item Level: 99\n" +
                "Version: Resurrected\n" +
                "Indestructible\n" +
                "+40% Faster Run/Walk\n" +
                "+50 to Life\n" +
                "+180 Maximum Stamina\n" +
                "Heal Stamina Plus 32%\n" +
                "Fire Resist +44%\n" +
                "10% Damage Taken Goes To Mana\n" +
                "Set (2 items): +15 to Dexterity\n" +
                "Set (3 items): +15 to Dexterity\n" +
                "Set (4 items): +15 to Dexterity\n" +
                "\n";
        byte[] bytes = {16, 64, -128, 0, 77, 38, -128, 27, 13, 22, -106, -64, 123, -29, -94, 8, -48, 64, 98, -63, 57, 32, 101, 1, 53, 7, -112, 19, -12, -64, -16, -28, 40, -104, -2, 23, -16, -6, 47, -32, -11, 95, -64, -21, 63};
        runItemDumpComparison(expected, loadD2Item(bytes));
    }

    @Test
    public void rareGloves() throws Exception {
        String expected = "Loath Clutches\n" +
                "Light Gauntlets\n" +
                "Defense: 17\n" +
                "Durability: 4 of 18\n" +
                "Required Level: 35\n" +
                "Required Strength: 45\n" +
                "Fingerprint: 0xe7208e05\n" +
                "Item Level: 55\n" +
                "Version: Resurrected\n" +
                "+2 to Javelin and Spear Skills (Amazon Only)\n" +
                "+20% Increased Attack Speed\n" +
                "3% Life stolen per hit\n" +
                "+49% Enhanced Defense\n" +
                "Fire Resist +14%\n" +
                "23% Better Chance of Getting Magic Items\n";
        byte[] bytes = {16, 0, -128, 0, -115, 42, -64, -84, 27, 10, 28, 65, -50, 111, 70, 109, 87, 73, 84, 69, -36, -4, 72, -71, 52, 11, 11, 72, 16, -128, 16, -29, -124, 53, 30, 3, 40, 123, 93, 80, -68, 4, 0, -12, 31};
        runItemDumpComparison(expected, loadD2Item(bytes));
    }

    @Test
    public void socketedHelm() throws Exception {
        String expected = "Gemmed Circlet\n" +
                "Circlet\n" +
                "Defense: 25\n" +
                "Durability: 15 of 35\n" +
                "Required Level: 41\n" +
                "Fingerprint: 0x589dd484\n" +
                "Item Level: 88\n" +
                "Version: Resurrected\n" +
                "+20 to Strength\n" +
                "2 Sockets (2 used)\n" +
                "Socketed: Fal Rune\n" +
                "Socketed: Fal Rune\n" +
                "\n" +
                "Fal Rune\n" +
                "Required Level: 41\n" +
                "Version: Resurrected\n" +
                "Weapons: +10 to Strength\n" +
                "Armor: +10 to Strength\n" +
                "Shields: +10 to Strength\n" +
                "\n" +
                "Fal Rune\n" +
                "Required Level: 41\n" +
                "Version: Resurrected\n" +
                "Weapons: +10 to Strength\n" +
                "Armor: +10 to Strength\n" +
                "Shields: +10 to Strength\n";
        byte[] bytes = {16, 8, -128, 0, 5, 72, 84, -4, -66, 19, 33, 117, 39, 22, 86, 48, -126, -111, 7, -14, 31, 16, 0, -96, 0, 53, 0, -32, 124, 92, 0, 16, 0, -96, 0, 53, 4, -32, 124, 92, 0};
        runItemDumpComparison(expected, loadD2Item(bytes));
    }

    @Test
    public void ear() throws Exception {
        String expected = "Tas's Ear\n" +
                "Version: Resurrected\n" +
                "Level 72 Necromancer\n";
        runItemDumpComparison(expected, loadD2Item(decode("10 00 A1 00 05 54 44 48 6A 78 0E 00")));
    }

    @Test
    public void khalimsEye() throws Exception {
        String expected = "Khalim's Eye\n" +
                "Version: Resurrected\n";
        runItemDumpComparison(expected, loadD2Item(decode("10 00 A0 00 05 90 64 73 40 15")));
    }

    @Test
    public void ear2() throws Exception {
        String expected = "Jeremy's Ear\n" +
                "Version: Resurrected\n" +
                "Level 59 Barbarian\n";
        runItemDumpComparison(expected, loadD2Item(decode("10 00 A1 00 05 88 84 3B 65 59 5E 6E E7 01 00")));
    }

    @Test
    public void healthPot() throws Exception {
        String expected = "Super Healing Potion\n" +
                "Version: Resurrected\n" +
                "Replenish Life +320\n";
        runItemDumpComparison(expected, loadD2Item(HEALTH_POT));
    }

    @Test
    public void moveItem() throws Exception {
        String expected = "Super Healing Potion\n" +
                "Version: Resurrected\n" +
                "Replenish Life +320\n";
        byte[] bytes = {16, 4, -96, 8, 21, 0, 0, 79, -76, 0};
        D2Item d2Item = loadD2Item(bytes);
        d2Item.set_location((short) 0);
        d2Item.set_col((short) 4);
        d2Item.set_row((short) 4);
        d2Item.set_panel((short) 1);
        D2Item afterMove = loadD2Item(d2Item.get_bytes());
        runItemDumpComparison(expected, afterMove);
        assertEquals((short) 0, afterMove.get_location());
        assertEquals((short) 4, afterMove.get_col());
        assertEquals((short) 4, afterMove.get_row());
        assertEquals((short) 1, afterMove.get_panel());
    }

    @Test
    public void cta() throws Exception {
        String expected = "Call to Arms\n" +
                "War Scepter\n" +
                "AmnRalMalIstOhm\n" +
                "One Hand Damage: 37 - 63\n" +
                "Durability: 68 of 70\n" +
                "Required Level: 57\n" +
                "Required Strength: 55\n" +
                "Fingerprint: 0x1baff84d\n" +
                "GUID: 0x0 0x0 0xb75ebe57 0xa491bdb\n" +
                "Item Level: 61\n" +
                "Version: Resurrected\n" +
                "+2 to All Skills\n" +
                "+40% Increased Attack Speed\n" +
                "273% Enhanced Damage\n" +
                "+150% Damage to Undead\n" +
                "Adds 5 - 30 Fire Damage\n" +
                "7% Life stolen per hit\n" +
                "Prevent Monster Heal\n" +
                "+9 to Battle Command\n" +
                "+13 to Battle Orders\n" +
                "+10 to Battle Cry\n" +
                "Replenish Life +12\n" +
                "30% Better Chance of Getting Magic Items\n" +
                "5 Sockets (5 used)\n" +
                "Socketed: Amn Rune\n" +
                "Socketed: Ral Rune\n" +
                "Socketed: Mal Rune\n" +
                "Socketed: Ist Rune\n" +
                "Socketed: Ohm Rune\n" +
                "\n" +
                "Amn Rune\n" +
                "Required Level: 25\n" +
                "GUID: 0x0 0x0 0x5203a1ac 0x67e15e4\n" +
                "Version: Resurrected\n" +
                "Weapons: 7% Life stolen per hit\n" +
                "Armor: Attacker Takes Damage of 14\n" +
                "Shields: Attacker Takes Damage of 14\n" +
                "\n" +
                "Ral Rune\n" +
                "Required Level: 19\n" +
                "GUID: 0x0 0x0 0x66f03f8f 0x70897b7\n" +
                "Version: Resurrected\n" +
                "Weapons: Adds 5 - 30 Fire Damage\n" +
                "Armor: Fire Resist +30%\n" +
                "Shields: Fire Resist +35%\n" +
                "\n" +
                "Mal Rune\n" +
                "Required Level: 49\n" +
                "GUID: 0x0 0x0 0x810819fd 0x5f39411\n" +
                "Version: Resurrected\n" +
                "Weapons: Prevent Monster Heal\n" +
                "Armor: Magic Damage Reduced by 7\n" +
                "Shields: Magic Damage Reduced by 7\n" +
                "\n" +
                "Ist Rune\n" +
                "Required Level: 51\n" +
                "GUID: 0x0 0x0 0xddb48852 0x569123e\n" +
                "Version: Resurrected\n" +
                "Weapons: 30% Better Chance of Getting Magic Items\n" +
                "Armor: 25% Better Chance of Getting Magic Items\n" +
                "Shields: 25% Better Chance of Getting Magic Items\n" +
                "\n" +
                "Ohm Rune\n" +
                "Required Level: 57\n" +
                "Version: Resurrected\n" +
                "Weapons: +50% Enhanced Damage\n" +
                "Armor: +5% to Maximum Cold Resist\n" +
                "Shields: +5% to Maximum Cold Resist\n";
        byte[] bytes = {16, 8, -128, 12, -53, 46, 0, -48, -84, 77, -8, -81, 27, 61, -31, 4, 42, 0, 0, 0, 0, 0, 0, 0, -64, -107, -81, -41, -19, -10, 70, -110, -126, 17, -111, -6, 31, -31, -37, 55, 37, 106, 23, 94, 24, 73, 74, -104, 74, 77, -104, 77, -55, 31, -3, 7, 16, 0, -96, 8, 51, 0, -32, 124, 62, 5, 0, 0, 0, 0, 0, 0, 0, 96, 13, 29, -112, 34, -81, -16, 51, 0, 16, 0, -96, 8, 51, 4, -32, 124, 35, 5, 0, 0, 0, 0, 0, 0, 0, 120, -4, -127, 55, -69, -67, 68, 56, 0, 16, 0, -96, 8, 51, 8, -32, 48, -37, 2, 0, 0, 0, 0, 0, 0, 0, -12, 103, 32, 4, 70, 80, -50, 23, 0, 16, 0, -96, 8, 51, 12, -32, 48, 95, 5, 0, 0, 0, 0, 0, 0, 0, -112, 66, -92, -19, -10, -111, 72, 43, 0, 16, 0, -96, 8, 51, 16, -32, 48, 62};
        runItemDumpComparison(expected, loadD2Item(bytes));
    }

    @Test
    public void titans() throws Exception {
        String expected = "BigBoobsBigBow's Titan's Revenge\n" +
                "Matriarchal Javelin\n" +
                "Throw Damage: 169 - 324\n" +
                "One Hand Damage: 149 - 274\n" +
                "Quantity: 142\n" +
                "Required Level: 55\n" +
                "Required Strength: 97\n" +
                "Required Dexterity: 141\n" +
                "Fingerprint: 0xac444728\n" +
                "Item Level: 87\n" +
                "Version: Resurrected\n" +
                "+2 to Javelin and Spear Skills (Amazon Only)\n" +
                "+2 to Amazon Skill Levels\n" +
                "+30% Faster Run/Walk\n" +
                "177% Enhanced Damage\n" +
                "Adds 25 - 50 Damage\n" +
                "5% Life stolen per hit\n" +
                "+20 to Strength\n" +
                "+20 to Dexterity\n" +
                "Increased Stack Size\n" +
                "Replenishes quantity\n" +
                "Required Level +7\n" +
                "Ethereal\n";
        byte[] bytes = decode("10 40 C0 01 0D 11 E0 59 39 A0 1C 11 B1 5E 8F 8C 10 4A 3B 13 7A 7B 13 9B 13 4A 3B 13 7A BB 03 60 50 C0 11 00 1A 01 B4 08 B1 62 55 C8 2C C8 78 14 A6 40 5C 0E 60 64 9F 32 50 32 5E 02 00 EA E7 F9 E3 F9 0F");
        runItemDumpComparison(expected, loadD2Item(bytes));
    }

    @Test
    public void charm() throws Exception {
        String expected = "Harpoonist's Grand Charm of Maiming\n" +
                "Grand Charm\n" +
                "Required Level: 63\n" +
                "Fingerprint: 0x9256fa58\n" +
                "Item Level: 85\n" +
                "Version: Resurrected\n" +
                "+1 to Javelin and Spear Skills (Amazon Only)\n" +
                "+3 to Maximum Damage\n";
        byte[] bytes = {16, 0, -128, 0, 5, 92, 68, -40, 109, -64, -46, -73, -110, -84, 82, -128, -115, -87, 88, 24, 96, 24, -128, 26, -16, 18, 0, -56, 127};
        runItemDumpComparison(expected, loadD2Item(bytes));
    }

    @Test
    public void ring() throws Exception {
        String expected = "Raven Frost\n" +
                "Ring\n" +
                "Required Level: 45\n" +
                "Fingerprint: 0x52eaf57b\n" +
                "Item Level: 90\n" +
                "Version: Resurrected\n" +
                "+191 to Attack Rating\n" +
                "Adds 15 - 45 Cold Damage Over 4 Secs (100 Frames)\n" +
                "+20 to Dexterity\n" +
                "+40 to Mana\n" +
                "+20 Cold Absorb\n" +
                "Cannot Be Frozen\n";
        byte[] bytes = {16, 0, -128, 0, -117, 25, -32, -4, -40, -80, 87, -81, 46, -91, -67, 51, 17, 4, -48, 18, 32, 77, -8, -59, -58, 67, 11, 50, 74, -108, 76, -1, 3};
        runItemDumpComparison(expected, loadD2Item(bytes));
    }

    @Test
    public void standardOfHeroes() throws Exception {
        String expected = "Standard of Heroes\n" +
                "Required Level: 90\n" +
                "Fingerprint: 0xa4366601\n" +
                "Item Level: 99\n" +
                "Version: Resurrected\n";
        byte[] bytes = decode("10008000055496CC1802CC6C48C7C7FFFB0F");
        runItemDumpComparison(expected, loadD2Item(bytes));
    }

    @Test
    public void coh() throws Exception {
        String expected = "Chains of Honor\n" + "Archon Plate\n"
                + "DolUmBerIst\n"
                + "Defense: 882\n"
                + "Durability: 15 of 60\n"
                + "Required Level: 63\n"
                + "Required Strength: 103\n"
                + "Fingerprint: 0xed9dd144\n"
                + "Item Level: 85\n"
                + "Version: Resurrected\n"
                + "+2 to All Skills\n"
                + "+200% Damage to Demons\n"
                + "+100% Damage to Undead\n"
                + "8% Life stolen per hit\n"
                + "+70% Enhanced Defense\n"
                + "+20 to Strength\n"
                + "Replenish Life +7\n"
                + "All Resistances +65\n"
                + "Damage Reduced by 8%\n"
                + "25% Better Chance of Getting Magic Items\n"
                + "4 Sockets (4 used)\n"
                + "Socketed: Dol Rune\n"
                + "Socketed: Um Rune\n"
                + "Socketed: Ber Rune\n"
                + "Socketed: Ist Rune\n"
                + "\n"
                + "Dol Rune\n"
                + "Required Level: 31\n"
                + "Version: Resurrected\n"
                + "Weapons: Hit Causes Monster to Flee +25%\n"
                + "Armor: Replenish Life +7\n"
                + "Shields: Replenish Life +7\n"
                + "\n"
                + "Um Rune\n"
                + "Required Level: 47\n"
                + "Version: Resurrected\n"
                + "Weapons: 25% Chance of Open Wounds\n"
                + "Armor: Cold Resist +15%\n"
                + "Lightning Resist +15%\n"
                + "Fire Resist +15%\n"
                + "Poison Resist +15%\n"
                + "Shields: Cold Resist +22%\n"
                + "Lightning Resist +22%\n"
                + "Fire Resist +22%\n"
                + "Poison Resist +22%\n"
                + "\n"
                + "Ber Rune\n"
                + "Required Level: 63\n"
                + "Version: Resurrected\n"
                + "Weapons: 20% Chance of Crushing Blow\n"
                + "Armor: Damage Reduced by 8%\n"
                + "Shields: Damage Reduced by 8%\n"
                + "\n"
                + "Ist Rune\n"
                + "Required Level: 51\n"
                + "Version: Resurrected\n"
                + "Weapons: 30% Better Chance of Getting Magic Items\n"
                + "Armor: 25% Better Chance of Getting Magic Items\n"
                + "Shields: 25% Better Chance of Getting Magic Items\n";
        byte[] bytes = {16, 72, -128, 12, -51, 12, 0, -102, 25, -119, -94, 59, -37, -85, 2, 10, -108, 8, -15, 60, -96, -1, 0, 104, 32, 24, 57, -95, 47, -123, -66, 21, -6, 90, -24, -29, -127, -56, -61, 77, 15, -98, 63, -6, 15, 16, 0, -96, 8, 53, 0, -32, 124, -66, 2, 16, 0, -96, 8, 51, 4, -32, 48, 76, 0, 16, 0, -96, 8, 53, 8, -32, 108, -65, 3, 16, 0, -96, 8, 53, 12, -32, 48, 95, 1};
        runItemDumpComparison(expected, loadD2Item(bytes));
    }

    @Test
    public void runeword_2_6() throws Exception {
        String expected = "Bulwark\n" +
                "Mask\n" +
                "ShaelIoSol\n" +
                "Defense: 38\n" +
                "Durability: 17 of 20\n" +
                "Required Level: 35\n" +
                "Required Strength: 23\n" +
                "Fingerprint: 0x4635278f\n" +
                "Item Level: 85\n" +
                "Version: Resurrected\n" +
                "+20% Faster Hit Recovery\n" +
                "4% Life stolen per hit\n" +
                "+76% Enhanced Defense\n" +
                "+10 to Vitality\n" +
                "Increase Maximum Life 5%\n" +
                "Replenish Life +30\n" +
                "Damage Reduced by 11%\n" +
                "Damage Reduced by 7\n" +
                "3 Sockets (3 used)\n" +
                "Socketed: Shael Rune\n" +
                "Socketed: Io Rune\n" +
                "Socketed: Sol Rune\n" +
                "\n" +
                "Shael Rune\n" +
                "Required Level: 29\n" +
                "Version: Resurrected\n" +
                "Weapons: +20% Increased Attack Speed\n" +
                "Armor: +20% Faster Hit Recovery\n" +
                "Shields: +20% Faster Block Rate\n" +
                "\n" +
                "Io Rune\n" +
                "Required Level: 35\n" +
                "Version: Resurrected\n" +
                "Weapons: +10 to Vitality\n" +
                "Armor: +10 to Vitality\n" +
                "Shields: +10 to Vitality\n" +
                "\n" +
                "Sol Rune\n" +
                "Required Level: 27\n" +
                "Version: Resurrected\n" +
                "Weapons: Armor: Damage Reduced by 7\n" +
                "Shields: Damage Reduced by 7\n";
        byte[] bytes = decode("10 08 80 04 05 00 D4 92 D4 1E 4F 6A 8C AA 02 BA 1A 10 50 44 98 FF 10 98 90 98 C6 83 A0 84 67 F2 FC 07 10 00 A0 00 35 00 E0 7C B6 01 10 00 A0 00 35 04 E0 7C F6 01 10 00 A0 00 35 08 E0 7C 98 00 10");
        runItemDumpComparison(expected, loadD2Item(bytes));
    }

    @Test
    public void new_charm() throws Exception {
        // TODO: This needs to be fixed
        String expected = "Flame Rift\n" + "Grand Charm\n"
                + "Required Level: 75\n"
                + "Fingerprint: 0x69f99b80\n"
                + "Item Level: 99\n"
                + "Version: Resurrected\n"
                + "Monster Fire Immunity is Sundered\n"
                + "Fire Resist +-86%\n";
        byte[] bytes = decode("10 00 80 00 05 0C 54 D8 6D 00 DC CC 4F 1B 5F 91 0C 27 E4 F4 62 E9 3F");
        runItemDumpComparison(expected, loadD2Item(bytes));
    }

    @Test
    public void cairn_stones_partial_read() throws Exception {
        D2Item d2Item = loadD2Item(decode("10 00 A0 00 05 C8 54 A5 31 00"));
        String expected = "Key to the Cairn Stones\n" +
                "Version: Resurrected\n";
        runItemDumpComparison(expected, d2Item);
        assertEquals(10, d2Item.getItemLength());
    }

    private byte[] decode(String s) {
        return BaseEncoding.base16().decode(s.replaceAll(" ", ""));
    }

    private void runItemDumpComparison(String expected, D2Item d2Item) {
        assertEquals(expected, d2Item.itemDump(true).replaceAll("\r", ""));
    }

    private D2Item loadD2Item(byte[] bytes) throws Exception {
        D2TxtFile.constructTxtFiles("./d2111");
        return new D2Item("my-test-file", new D2BitReader(bytes), 10);
    }
}