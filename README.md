# 说明
- 本工程仅提供demo供参照，部分实现不一定为最优方案，请根据业务场景扩展和谨慎选择。

# 启动
- 1、修改 SSEContents 中连接串
- 2、直接运行 Application.java

# demo
## sse
- 1、
```bash
curl --location --request GET 'http://localhost:8080/sse' \
--header 'Content-Type: application/json'
```

- 2、
```bash
curl --location --request GET 'http://localhost:8080/sse' \
--header 'Content-Type: application/json' \
--data '{
  "referenceData": {
    "contents": [
      "    总的来说，今年一季度我国城镇新增就业取得了较好的成绩，就业形势总体稳定。政府在促进就业方面采取了一系列措施，为就业提供了有利条件。同时，推出全国就业公共服务信息平台也将为就业服务工作提供更好的支持和保障。展望未来，我们有理由相信，我国就业形势将继续保持稳定，为经济社会发展提供有力支撑。"
    ]
  },
  "prompt": "toEnglish"
}'
```
