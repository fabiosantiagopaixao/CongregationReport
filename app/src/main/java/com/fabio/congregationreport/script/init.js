function doGet(request) {
  if (request.parameter.spreadsheetId === undefined) {
    return ContentService.createTextOutput(
      JSON.stringify({ error: 'Parameter spreadsheetId not found!' })
    );
  }
  if (request.parameter.sheet === undefined) {
    return ContentService.createTextOutput(
      JSON.stringify(
        getAllData(
          request.parameter.spreadsheetId
        )
      )
    );
  } else {
    return ContentService.createTextOutput(
      JSON.stringify(
        getData(
          request.parameter.spreadsheetId,
          request.parameter.sheet
        )
      )
    );
  }

}

function getData(spreadsheetId, sheet) {
  var rangName = sheet + '!A:Z';
  var values = Sheets.Spreadsheets.Values.get(spreadsheetId, rangName).values;
  if (!values) {
    return { error: 'No data found' };
  } else {
    var data = [];

    for (var row = 1; row < values.length; row++) {
      var item = {};

      for (var column = 0; column < 26; column++) {
        item[values[0][column]] = values[row][column];
      }
      data.push(item);
    }
    return data;
  }
}

function getAllData(spreadsheetId) {
  var rangs = getRangsNames();
  var response = [];
  for (var i = 0; i < rangs.length; i++) {

    var sheet = rangs[i];
    var rangName = sheet + '!A:Z';
    var values = Sheets.Spreadsheets.Values.get(spreadsheetId, rangName).values;
    if (!values) {
      return { error: 'No data found' };
    } else {
      var data = [];

      for (var row = 1; row < values.length; row++) {
        var item = {};

        for (var column = 0; column < 26; column++) {
          item[values[0][column]] = values[row][column];
        }
        data.push(item);
      }
      data;
    }

    // Add os dados da aba
    response.push({ sheet, data });
  }
  return { data: response };
}


function getRangsNames() {
  var listRangs = [];
  listRangs.push('setting');
  listRangs.push('user');
  listRangs.push('group');
  listRangs.push('publisher');

  return listRangs;
}