## TODO
* Lucene
* OpenNLP
* Tika
* Hibernate Search
* [Information Retrieval - Venkatesh Vinayakarao](https://youtube.com/playlist?list=PLg38siQiGwWHdLUMo_UIPlYTh5cxTWRSB)
* https://archive.google/jobs/britney.html

## indexes
* represent a large boolean sparse matrix with dense matrix/linkedList, instead of array/arrayList, where we keep the ones (or the zeros) and the index: `{0,0,0,1,0,1,...0} -> 10,000 items`, `[3,5]` -> 2 items
* tokenization -> words only, no punctuations
* inverted index: term, doc frequency, doc ids (postings)
* AND -> merge two postings with items present in both (n+m),
* but postings can be added into a tree too for log-based solution
  * or use a SkipList (with skip pointers, sqrt(n) is a good practical distance)
  * or multi-level skip list, as in lucene
*  positional index -> point 3 + positions inside a doc. It is a bit expensive
* we may include well-known bi-words inside the index (e.g. Taj Mahal)
* basic index types:
  * forward idx, inverted idx, inverted positional idx,
  * permuterm idx (for wildcard queries), k-gram idx, composite idx

## tokens
* take the doc, what is a doc :)
* tokenize, how to tokenize :) , stop words,
* (normalization) equivalence and case (e.g. color, colour; anti-theft, anti theft),
* (e.g. Window, window; C.A.T. is nor cat; Bush is not bush)
* stemming (chop the ends): going -> go, analysis -> analys, not a dictionary word
  * example is Porter Stemmer, Lovins, Paice ...
* lemmatization: saw -> see

## Queries

### token-level query processing
* query segmentation
* spelling correction
  * finding "nearest" dictionary item vs "most commonly used" dictionary item
    * edit distance, levenshtein,
    * Jaccard Similarity between k-grams of matched term and query term
  * isolated-term corrections vs context-sensitive correction.
    * "flew form New York", where "form" is correct term, but must be "from"
* phonetic correction

### query types
* Navigational: "fb" instead of "facebook"
* Informational: "Heidegger"
* Transactional: "Sofia to Florence air ticket"
* Short phrases: "data science degree in Sofia"
* Long queries: ...
* Head and tail (rare) queries, based on frequency.
  * Pizza Delivery, vs Pizza Delivery in Boston
* Question / Answer

### query understanding
* token-level processing: spelling errors, query segmentation
* query reduction: remove less important tokens
* query expansion: add items to improve precision and recall
  * use a more common synonym
* query rewriting: into retrieval system friendlier manner
* wildcard queries
  * for wildcard at end, use B-tree
  * for wildcard at beginning, we could have a backwards B-tree
  * for wildcard in the middle, use k-gram index

### query result scoring - vector space model here, but there are other too.
* each word is a dimension of a vector, and we use Jaccard Similarity to compare the two sets. Note: set, not list.
* ranked retrieval with bag(list) instead of set.
* sentence comparison: cosine similarity (similarity between two vectors)
  * for 3D vector space (the, ssn, chennai): "the ssn" 110, "the chennai" 101. So compare the angles for the two 2d vectors
  * use cosine to make 0-90 degrees to 1-0. Perpendicular - dissimilar, parallel - same, hence cosine. We need the rank only, that's why a monotonous function does the job.
  * then use dot product divided by (l2 norm of query * l2 norm of dict) // to calculate cosine of non-unit vectors
  * as not every word is important (e.g. the) a weight can be added.
  * note: dot product similarity takes into account the length of the vectors too, which is not important here.
  * dot product for unit vector must be the same as cosine !?
  * yet there is a bias towards longer documents, despite adding length normalization
  * another thing to think about: distinct terms in compared documents.
  * OKAPI ranking function: BM25 (for best match), BM...
  * VSM is expensive, so: index elimination, pre-computed popular queries, popularity, cluster pruning, proximity of idxs in doc, and other heuristics
* What weights to add to each term frequency (tf) on every word? inverse document frequency (idf), but see TF-IDF formulas in wikipedia.

## Index compression
* rule of 30: 30 most common words
* Heap's law: M=kT^b , M is vocabulary size, T is number of tokens
* Zipf's law: frequency and rank are inverse proportional, ith most freq term freq is proportional to 1/i
* front coding for strings
* variable byte encoding for numbers (good one :))
  * 824 = (2^7 * 6) + 56, so we use two numbers: 6,56
  * 5 = (2^7 * 0) + 5, so: 5
  * 214577 = (2^7 * 13) + 12 + 49, so: 13,12,49
  * 7 bits for each digit plus 1 more for end (a.k.a. continuation bit) used at the beginning of the last byte (56)
  * for all 6,56,5,13,12,49 : 00000110 10111000 10000101 0001101 00001100 ...
  * for the example of 214577/128 = 1676 with reminder 49, 1676/128 = 13 with reminder 12, 13/128 = 0 with reminder 13. So 214577 = (((13*128)+12)*128)+49

## documents with structure
* zones (abstract, hypothesis, ..., or arbitrary free text) and fields (title, author, ...)
* create an index for each zone: inverted idx, parametric idx for fields, zone indexes...

## evaluation (video 17)
* precision/recall, F-metric (harmonic mean of recall and precision)
* mean average precision
* mean reciprocal rank
* NDCG non-boolean/graded relevance score
* probability ranking retrieval, bayes optimal decision rule, binary independence model

## relevance feedback + synonyms (video 18)
* local refinement: use query or results for reformulating the query: 
  * relevance feedback (Rocchio)
  * indirect (implicit) relevance feedback
  * pseudo (blind) relevance
* global refinement: do not use the query + results, but instead: spelling correction, thesaurus, co-occurrence analysis
* Page Rank, HITS

## Lucene architecture
* Query parser -> Searcher and Language Analyser -> Index -> Store