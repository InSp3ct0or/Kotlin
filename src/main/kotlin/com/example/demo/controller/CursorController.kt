package com.example.demo.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import javax.sql.DataSource
import org.springframework.beans.factory.annotation.Autowired
import java.sql.Connection
import java.sql.CallableStatement

@Controller
class CursorController @Autowired constructor(private val dataSource: DataSource) {

    @GetMapping("/cursors")
    fun showCursorsPage(model: Model): String {
        return "cursors"
    }

    @PostMapping("/executeCursor")
    fun executeCursor(model: Model): String {
        val results = mutableListOf<String>()
        val sql = """
            DECLARE
                v_count NUMBER;
                v_max_capacity NUMBER;
                CURSOR animal_cursor IS
                    SELECT animalid, name, enclosure_enclosureid FROM animal WHERE enclosure_enclosureid = 1;
                v_animal_id NUMBER;
                v_animal_name VARCHAR2(255);
                v_current_enclosure NUMBER;
            BEGIN
                SELECT COUNT(*) INTO v_count FROM animal WHERE enclosure_enclosureid = 1;
                SELECT capacity INTO v_max_capacity FROM enclosure WHERE enclosureid = 1;
                IF v_count > v_max_capacity THEN
                    OPEN animal_cursor;
                    LOOP
                        FETCH animal_cursor INTO v_animal_id, v_animal_name, v_current_enclosure;
                        EXIT WHEN animal_cursor%NOTFOUND;
                        UPDATE animal SET enclosure_enclosureid = 2 WHERE animalid = v_animal_id;
                        DBMS_OUTPUT.PUT_LINE('Moved animal: ' || v_animal_name || ' to new enclosure.');
                    END LOOP;
                    CLOSE animal_cursor;
                END IF;
            END;
        """

        dataSource.connection.use { conn ->
            conn.createStatement().use { stmt ->
                stmt.execute(sql)
            }

            conn.createStatement().use { stmt ->
                val rs = stmt.executeQuery("SELECT * FROM animal WHERE enclosure_enclosureid = 2")
                while (rs.next()) {
                    val animalName = rs.getString("name")
                    results.add("Moved animal: $animalName to new enclosure.")
                }
            }
        }

        model.addAttribute("results", results)
        return "cursors"
    }
}