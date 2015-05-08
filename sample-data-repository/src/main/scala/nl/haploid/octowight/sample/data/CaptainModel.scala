package nl.haploid.octowight.sample.data

import java.lang

import nl.haploid.octowight.registry.data.Model

import scala.beans.BeanProperty

class CaptainModel extends Model {
  @BeanProperty var id: lang.Long = null
  @BeanProperty var name: String = null
}
