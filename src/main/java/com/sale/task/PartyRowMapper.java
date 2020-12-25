package com.sale.task;

import com.sale.task.model.Party;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PartyRowMapper implements RowMapper<Party> {
    @Override
    public Party mapRow(ResultSet resultSet, int i) throws SQLException {
        Party party = new Party();
        party.setId(resultSet.getInt("id"));
        party.setDate(resultSet.getDate("create_date"));
        party.setVersion(resultSet.getInt("version"));
        party.setName(resultSet.getString("name"));
        return party;
    }
}
