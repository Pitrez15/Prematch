const sql = require('mssql');
const sqlConfig = require('../config/database');


// Database Connection
const pool1 = new sql.ConnectionPool(sqlConfig);
const pool1Connect = pool1.connect();

pool1.on('error', err => {

    console.log(err);
})


// Method to list all Teams:

exports.listAllTeams = async (req, res) => {

  await pool1Connect;

    try {

        const request = pool1.request();
        var scriptSQL = `select TEAM_ID,TEAM_NAME,TEAM_INITIALS,TEAM_CITY,TEAM_PRIMARY_COLOR,TEAM_SECONDARY_COLOR,
                                TEAM_EMAIL,TEAM_PHONE,TOURNAMENT_ID from TEAMS`;

        const result = await request.query(scriptSQL);
        console.log(scriptSQL);
        console.dir(result.recordset);
        res.status(201).json(result.recordset);
    } 
    catch (err) {

        console.error('SQL error', err);
        res.status(500).send({mensagem: "Error connecting to DB"})
    }
};


//Method to create new Team:

exports.createNewTeam = async (req, res) => {

    const { TEAM_ID,TEAM_NAME,TEAM_INITIALS,TEAM_CITY,TEAM_PRIMARY_COLOR,TEAM_SECONDARY_COLOR,TEAM_EMAIL,TEAM_PHONE,TOURNAMENT_ID } = req.body;

    await pool1Connect; 

    try {

        const request = pool1.request();
        var scriptSQL = `insert into TEAMS (TEAM_ID,TEAM_NAME,TEAM_INITIALS,TEAM_CITY,TEAM_PRIMARY_COLOR,TEAM_SECONDARY_COLOR,TEAM_EMAIL,TEAM_PHONE,TOURNAMENT_ID) values 
                                                (`+TEAM_ID+`,'`+TEAM_NAME+`','`+TEAM_INITIALS+`','`+TEAM_CITY+`','`+TEAM_PRIMARY_COLOR+`','`+TEAM_SECONDARY_COLOR+`',
                                                '`+TEAM_EMAIL+`',`+TEAM_PHONE+`,`+TOURNAMENT_ID+`)`;

        const result = await request.query(scriptSQL);
        console.log(scriptSQL);
        console.dir(result.recordset);
        res.status(201).send({mensagem: "Team Created"});
    } 
    catch (err) {

        console.error('SQL error', err);
        res.status(500).send({mensagem: "Error connecting to DB"})
    }
};


// Method to list Team by TEAM_ID:

exports.listTeamByTeamID = async (req, res) => {

    const TEAM_ID = parseInt(req.params.TEAM_ID)

    await pool1Connect;
  
      try {
  
          const request = pool1.request();
          var scriptSQL = `select TEAM_ID,TEAM_NAME,TEAM_INITIALS,TEAM_CITY,TEAM_PRIMARY_COLOR,TEAM_SECONDARY_COLOR,TEAM_EMAIL,TEAM_PHONE,TOURNAMENT_ID
                            from TEAMS where TEAM_ID =` +TEAM_ID;
          const result = await request.query(scriptSQL);
          console.log(scriptSQL);
          console.dir(result.recordset);
          res.status(201).json(result.recordset);
      } 
      catch (err) {
  
          console.error('SQL error', err);
          res.status(500).send({mensagem: "Error connecting to DB"})
      }
  };



// Method to list All Teams by TOURNAMENT_ID:

exports.listTeamByTournamentID = async (req, res) => {

    const TOURNAMENT_ID = parseInt(req.params.TOURNAMENT_ID)

    await pool1Connect;
  
      try {
  
          const request = pool1.request();
          var scriptSQL = `select TEAM_ID,TEAM_NAME,TEAM_INITIALS,TEAM_CITY,TEAM_PRIMARY_COLOR,TEAM_SECONDARY_COLOR,TEAM_EMAIL,TEAM_PHONE,TOURNAMENT_ID 
                            from TEAMS where TOURNAMENT_ID =` +TOURNAMENT_ID;
          const result = await request.query(scriptSQL);
          console.log(scriptSQL);
          console.dir(result.recordset);
          res.status(201).json(result.recordset);
      } 
      catch (err) {
  
          console.error('SQL error', err);
          res.status(500).send({mensagem: "Error connecting to DB"})
      }
  };


  // Method to update a Team:

exports.updateTeam = async (req, res) => {

    const TEAM_ID = parseInt(req.params.TEAM_ID);

    const { TEAM_NAME,TEAM_INITIALS,TEAM_CITY,TEAM_PRIMARY_COLOR,TEAM_SECONDARY_COLOR,TEAM_EMAIL,TEAM_PHONE } = req.body;

    await pool1Connect;

    try {

        const request = pool1.request();
        var scriptSQL = `update TEAMS set TEAM_NAME='`+TEAM_NAME+`',TEAM_INITIALS='`+TEAM_INITIALS+`',TEAM_CITY='`+TEAM_CITY+`',TEAM_PRIMARY_COLOR='`+TEAM_PRIMARY_COLOR+`',
                                        TEAM_SECONDARY_COLOR='`+TEAM_SECONDARY_COLOR+`',TEAM_EMAIL='`+TEAM_EMAIL+`',TEAM_PHONE=`+TEAM_PHONE+`
                                        where TEAM_ID=` +TEAM_ID;
        const result = await request.query(scriptSQL);
        console.log(scriptSQL);
        res.status(201).send({mensagem: "Team Updated"});
    } catch (err) {
        console.error('SQL error', err);
        res.status(500).send({mensagem: "Database error"})
    }
};


// Method for deleting team:

exports.deleteTeam = async (req, res) => {

    const TEAM_ID = parseInt(req.params.TEAM_ID);

    await pool1Connect; 

    try {

        const request = pool1.request();
        var scriptSQL = `delete from TEAMS where TEAM_ID=` +TEAM_ID;
        const result = await request.query(scriptSQL);
        console.log(scriptSQL);
        res.status(201).send({mensagem: "Team deleted"});
    } catch (err) {
        console.error('SQL error', err);
        res.status(500).send({mensagem: "Connection error"})
    }
};

// Method for deleting teams by Tournament:

exports.deleteTeamsByTournament = async (req, res) => {

    const TOURNAMENT_ID = parseInt(req.params.TOURNAMENT_ID);

    await pool1Connect; 

    try {

        const request = pool1.request();
        var scriptSQL = `delete from TEAMS where TOURNAMENT_ID=` +TOURNAMENT_ID;
        const result = await request.query(scriptSQL);
        console.log(scriptSQL);
        res.status(201).send({mensagem: "Team deleted"});
    } catch (err) {
        console.error('SQL error', err);
        res.status(500).send({mensagem: "Connection error"})
    }
};