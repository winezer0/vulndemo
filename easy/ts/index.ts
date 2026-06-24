import { FuncCall } from './FuncCall';

const cmd = process.argv[2] || "calc";
// FuncCall.runByChild(cmd);
FuncCall.runByInterface(cmd);
