package kr.sul.party.tab

import kr.sul.party.Main.Companion.plugin
import org.bukkit.entity.Player
import pl.mnekos.tablist.TabEntry
import pl.mnekos.tablist.TablistGenerator

object TablistGeneratorImpl : TablistGenerator(plugin) {
    override fun generateHeaderFooter(p: Player): Array<String> {
        val list = arrayListOf<String>()
        list.add("HEADER")
        list.add("FOOTER")
        return list.toTypedArray()
    }

    override fun generateBars(p: Player): Array<TabEntry> {
        val list = arrayListOf<TabEntry>()
        for (i in 1..80) {
            list.add(TabEntry("Bar$i"))
        }
        return list.toTypedArray()
    }
}