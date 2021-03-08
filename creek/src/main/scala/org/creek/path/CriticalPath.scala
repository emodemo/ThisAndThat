package org.creek.path

import org.creek.collection.Graph

object CriticalPath {

  //TODO: need to create new graph from the previous one but the previous needs to have specific information
  def acyclic[N](graph: Graph[N]): Unit = {
    // pp664 / 676
    // duplicate vertices - one for start and one for end
    // add edge between them with weight = duration
    // add edge for each parentOnPath from parent_end to current_start with weight = 0
    // create source and sink nodes
    // add edge from source to each start job with weight = 0
    // add edge from each job end to sink with weight = 0

  }

}
