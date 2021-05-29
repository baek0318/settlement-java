### 📄 API document

## User

### `POST` /user
#### parameter
|Name|Type|In|Description|
|---|---|---|---|
|email|String|body| |
|nickName|String|body| |
|password|String|body| |

<details>
<summary>
응답
</summary>

```json
{
  "id" : "Long"
}
```
</details>


### `POST` /user/login
#### parameters
|Name|Type|In|
|---|---|---|
|email|String|body|
|password|String|body|

<details>
<summary> 응답 </summary>

```json
{
  "id": "Long"
}
```
</details>


