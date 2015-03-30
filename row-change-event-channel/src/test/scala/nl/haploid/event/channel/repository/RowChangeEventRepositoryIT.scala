package nl.haploid.event.channel.repository

import org.junit.Assert
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import nl.haploid.event.channel.AppConfiguration
import nl.haploid.event.channel.HsqlConfiguration

@RunWith(classOf[SpringJUnit4ClassRunner])
@ContextConfiguration(classes = Array(classOf[AppConfiguration], classOf[HsqlConfiguration]))
class RowChangeEventRepositoryIT {

	@Autowired
	val repository : RowChangeEventRepository = null

	@Test
	def testFindAll : Unit = {
		val events = repository.findAll
		Assert.assertNotNull(events)
	}
}
