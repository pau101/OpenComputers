package li.cil.oc.common.item

import java.util
import li.cil.oc.Config
import net.minecraft.client.renderer.texture.IconRegister
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack

class HardDiskDrive(val parent: Delegator, val megaBytes: Int) extends Delegate {
  val unlocalizedName = "HardDiskDrive" + megaBytes + "m"

  override def addInformation(item: ItemStack, player: EntityPlayer, tooltip: util.List[String], advanced: Boolean) = {
    super.addInformation(item, player, tooltip, advanced)

    if (item.hasTagCompound) {
      val nbt = item.getTagCompound
      if (nbt.hasKey(Config.namespace + "data")) {
        val data = nbt.getCompoundTag(Config.namespace + "data")
        if (data.hasKey(Config.namespace + "fs.label")) {
          tooltip.add(data.getString(Config.namespace + "fs.label"))
        }
        if (data.hasKey(Config.namespace + "node.address")) {
          tooltip.add(data.getString(Config.namespace + "node.address"))
        }
        if (advanced && data.hasKey("fs")) {
          val fsNbt = data.getCompoundTag("fs")
          if (fsNbt.hasKey("capacity.used")) {
            val used = fsNbt.getLong("capacity.used")
            tooltip.add("Disk usage: %d/%d Byte".format(used, megaBytes * 1024 * 1024))
          }
        }
      }
    }
  }

  override def registerIcons(iconRegister: IconRegister) {
    super.registerIcons(iconRegister)

    icon = iconRegister.registerIcon(Config.resourceDomain + ":hdd" + megaBytes)
  }
}