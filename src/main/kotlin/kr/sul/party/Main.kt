package kr.sul.party

import kr.sul.party.command.PartyCommand
import kr.sul.party.party.MemberKillDeathManager
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.JavaPluginLoader
import java.io.File

class Main : JavaPlugin {
    constructor() : super() {}
    constructor(loader: JavaPluginLoader?, description: PluginDescriptionFile?, dataFolder: File?, file: File?) : super(loader, description, dataFolder, file) {}

    companion object {
        lateinit var plugin: Plugin private set
    }

    override fun onEnable() {
        plugin = this
        registerClasses()
    }
    private fun registerClasses() {
        Bukkit.getPluginManager().registerEvents(MemberKillDeathManager, this)
        getCommand("파티").executor = PartyCommand
    }
}