package uk.co.renbinden.repair.system

import uk.co.renbinden.ilse.ecs.entity.Entity
import uk.co.renbinden.ilse.ecs.system.IteratingSystem
import uk.co.renbinden.repair.component.Collider
import uk.co.renbinden.repair.component.Dimensions
import uk.co.renbinden.repair.component.Position
import uk.co.renbinden.repair.component.Velocity

class CollisionSystem : IteratingSystem({
    it.has(Collider)
            && it.has(Position)
            && it.has(Velocity)
            && it.has(Dimensions)
}, priority = 3) {

    override fun processEntity(entity: Entity, dt: Double) {
        val collider = entity[Collider]
        val position = entity[Position]
        val velocity = entity[Velocity]

        val entities = engine.entities.filter { it.has(Collider) && it != entity }.map { it[Collider].collider }

        if (collider.collider.test(x = position.x + (velocity.dx * dt)).resolveHorizontal(entities))
            velocity.dx = 0.0

        if (collider.collider.test(y = position.y + (velocity.dy * dt)).resolveVertical(entities))
            velocity.dy = 0.0
    }

}