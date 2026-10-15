import { fastify as create } from 'fastify';
import * as Fastify from 'fastify';

const named = create();
const namespaced = Fastify.fastify();
const requireFactory = require('fastify');
const commonjs = requireFactory();
named.get('/named-factory', handler);
namespaced.get('/namespace-factory', handler);
commonjs.get('/commonjs-factory', handler);
