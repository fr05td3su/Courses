def web_application(environment_variables, send_headers):
	response_code = "200 OK"
	headers = ""
	for item in environment_variables.keys():
		print(item, environment_variables[item])
	send_headers(response_code, headers)
	print()
	query = environment_variables['QUERY_STRING']
	args = query.split('&')
	#args_dict = [args[i].split('=') for i in range(0, len(args))]
	#print(args_dict)
	return [reduce(lambda acc, cur: str(acc)+str(cur)+'\n', args, '')]
