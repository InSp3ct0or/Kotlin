package com.example.demo.service

import com.example.demo.entity.Enclosure
import com.example.demo.repository.EnclosureRepository
import org.springframework.stereotype.Service

@Service
class EnclosureService(private val enclosureRepository: EnclosureRepository) {

    fun getEnclosureHierarchy(): List<EnclosureNode> {
        val enclosures = enclosureRepository.findAll()
        enclosures.forEach { println(it) } // Отладочное сообщение

        val enclosureMap = enclosures.associateBy { it.enclosureId }
        val rootNodes = mutableListOf<EnclosureNode>()
        val childMap = mutableMapOf<Long, MutableList<EnclosureNode>>()

        enclosures.forEach { enclosure ->
            val node = EnclosureNode(enclosure)
            val parentId = enclosure.parentEnclosure?.enclosureId
            if (parentId != null && enclosureMap.containsKey(parentId)) {
                childMap.computeIfAbsent(parentId) { mutableListOf() }.add(node)
            } else {
                rootNodes.add(node)
            }
        }

        childMap.forEach { (parentId, children) ->
            enclosureMap[parentId]?.let { _ ->
                val parentNode = rootNodes.find { it.enclosure.enclosureId == parentId }
                parentNode?.children?.addAll(children)
            }
        }

        rootNodes.forEach { println(it) } // Отладочное сообщение

        return rootNodes
    }

    data class EnclosureNode(
        val enclosure: Enclosure,
        val children: MutableList<EnclosureNode> = mutableListOf()
    )
}


