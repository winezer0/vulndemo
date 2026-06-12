const { execSync } = require('child_process');
const axios = require('axios');

/**
 * 跨文件函数调用 - 命令执行（外部可控数据源）
 * @param {string} input - 用户输入参数
 */
function crossFileCmdExec(input) {
  // 漏洞点: 命令执行 - 跨文件函数直接拼接用户输入
  const result = execSync(`echo ${input}`, { encoding: 'utf-8' });
  return result;
}

/**
 * 跨文件函数调用 - 命令执行（硬编码数据源）
 */
function crossFileCmdExecHardcoded() {
  // 漏洞点: 命令执行 - 跨文件函数使用硬编码参数
  const result = execSync('echo hardcoded_crossfile_123', { encoding: 'utf-8' });
  return result;
}

/**
 * 跨文件函数调用 - 代码执行（外部可控数据源）
 * @param {string} input - 用户输入参数
 */
function crossFileCodeExec(input) {
  // 漏洞点: 代码执行 - 跨文件函数直接eval用户输入
  const result = eval(input);
  return result;
}

/**
 * 跨文件函数调用 - 代码执行（硬编码数据源）
 */
function crossFileCodeExecHardcoded() {
  // 漏洞点: 代码执行 - 跨文件函数eval硬编码字符串
  const result = eval('"crossfile_hardcoded_eval" + 100');
  return result;
}

/**
 * 跨文件函数调用 - SSRF（外部可控数据源）
 * @param {string} url - 用户输入URL
 */
async function crossFileSSRF(url) {
  // 漏洞点: SSRF - 跨文件函数直接请求用户URL
  const response = await axios.get(url);
  return response.data;
}

/**
 * 跨文件函数调用 - SSRF（硬编码数据源）
 */
async function crossFileSSRFHardcoded() {
  // 漏洞点: SSRF - 跨文件函数请求硬编码URL
  const response = await axios.get('http://127.0.0.1:8080/internal');
  return response.data;
}

module.exports = {
  crossFileCmdExec,
  crossFileCmdExecHardcoded,
  crossFileCodeExec,
  crossFileCodeExecHardcoded,
  crossFileSSRF,
  crossFileSSRFHardcoded
};
