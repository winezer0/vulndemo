const FuncCall = require('./FuncCall');

const cmd = process.argv[2] || "calc";
// FuncCall.runByChild(cmd);
FuncCall.runByInterface(cmd);
