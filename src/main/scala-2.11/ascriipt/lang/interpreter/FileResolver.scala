package ascriipt.lang.interpreter

import java.io.File

object FileResolver {
  def resolveFile(baseFile: File, path: String): File = {
    path.split("/").foldLeft(baseFile)(new File(_, _)).getCanonicalFile
  }
}
