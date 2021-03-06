---
title: "Extract syllables of neumes from XML"
layout: page
---


## Prepare a corpus

Load the libraries you'll use, create a digital text corpus as [explained in this tutorial](../corpus), and select an edition of neumes.

```tut:invisible
import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.mid.latinmodel._
import edu.holycross.shot.latin._
import edu.holycross.shot.virgapes._

val catalog = "editions/catalog.cex"
val citation = "editions/citation.cex"
val editions = "editions"
```

```tut:silent
// after doing all imports, defining catalog, citation
// and editions values...
val repo = TextRepositorySource.fromFiles(catalog,citation,editions)
val neumeCorpus = repo.corpus ~~ CtsUrn("urn:cts:chant:antiphonary.einsiedeln121.neumes_xml:")
```


## Extract string values from XML

The `latinmodel` package has a `collectText` function that extracts the content of text nodes from an XML string.  We use that to take all the nodes in an XML corpus, and create a sequence of nodes with the same URNs, but just the extracted text.

```tut:silent
val plainText = neumeCorpus.nodes.map(cn => CitableNode(cn.urn, edu.holycross.shot.mid.latinmodel.collectText(cn.text)))
```

The `virgapes` library includes a `Syllabifier` object that will create a sequence of `Syllable`s from a citable node.

```tut:silent
val syllabifiled = plainText.map(Syllabifier(_))
```
