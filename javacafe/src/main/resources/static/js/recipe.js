
async function getList(rno){
	const result = await axios.get(`/recipestock/list/${rno}`)
	return result.data
}

async function addStock(stockObj){
	const response = await axios.post(`/recipestock/`, stockObj)
	return response.data
}

async function duplicateCheck(stockObj){
	const response = await axios.post(`/recipestock/check`, stockObj)
	return response.data
}

async function getStock(rsno){
	const response = await axios.get(`/recipestock/${rsno}`)
	return response.data
}

async function modifyStock(stockObj){
	const response = await axios.put(`/recipestock/${stockObj.rsno}`, stockObj)
	return response.data
}