package edu.holycross.shot.mid.chant
import edu.holycross.shot.cite._
import edu.holycross.shot.mid.validator._
/** An orthographic system for neumes encoded in the
* Virgapes encoding, registered to a specific text or set
* of texts identified by CtsUrn.
*
* @param domain A set of one or more texts (depending
* on the hierarchical level of domain) identified by a CtsUrn.
*/
case class ChantLatin(domain: CtsUrn) extends MidOrthography {

  def orthography = "Latin"

  val tab = 0x9
  val nl = 0xA
  val cr = 0xD
  val space = 0x20
  val whiteSpace = Vector(space, tab, nl, cr)

  val period = 0x2e
  val hyphen = 0x2d
  val punctuation = Vector(period, hyphen)

  val upper = (0x41 to 0x5a).toVector
  val lower = (0x61 to 0x7a).toVector
  val alpha = upper ++ lower

  /** All valid code points. */
  val cpList:  Vector[Int] =  alpha ++ punctuation


  /** True if cp is a valid code point in the
  * Virgapes encoding.
  *
  * @param cp Codepoint to check.
  */
  def validCP(cp: Int): Boolean = {
    cpList.contains(cp)
  }

  /** Categories of tokens recognized by this orthography.*/
  def tokenCategories = {
    Vector(NeumeToken, PunctuationToken)
  }

  /** Tokenize a String.
  *
  * @param s String to tokenize.
  */
  def tokenizeString(s: String): Vector[MidToken] = {
    val expandHyphens = s.replaceAll("-", " - ")
    val tokens = expandHyphens.split("\\s+").filter(_.nonEmpty)
    val pairs = for (t <- tokens) yield {
      if (t == "-") {
        MidToken(t,Some(PunctuationToken))
      } else if (validString(t)) {
        val parts = t.split("\\.")
        if (parts.size == 4) {
          MidToken(t,Some(NeumeToken))
        } else {
          MidToken(t,None)
        }

      } else {
        MidToken(t,None)
      }
    }
    pairs.toVector
  }
}